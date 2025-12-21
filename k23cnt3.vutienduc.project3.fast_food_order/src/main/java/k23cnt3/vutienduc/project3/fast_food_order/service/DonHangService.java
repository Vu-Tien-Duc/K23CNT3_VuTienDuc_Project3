package k23cnt3.vutienduc.project3.fast_food_order.service;

import k23cnt3.vutienduc.project3.fast_food_order.entity.*;
import k23cnt3.vutienduc.project3.fast_food_order.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DonHangService {

    private final DonHangRepository donHangRepository;
    private final NguoiDungRepository nguoiDungRepository;
    private final MonAnRepository monAnRepository;
    private final GiamGiaRepository giamGiaRepository;
    private final ChiTietDonHangRepository chiTietDonHangRepository;
    private final ThanhToanRepository thanhToanRepository;

    public long countAll() {
        return donHangRepository.count();
    }
    private NguoiDung getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return nguoiDungRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
    }

    @Transactional
    public DonHang create(Map<String, Object> request) {
        NguoiDung nguoiDung = getCurrentUser();

        DonHang donHang = new DonHang();
        donHang.setNgayDat(LocalDateTime.now());
        donHang.setDiaChiGiao((String) request.get("diaChiGiao"));
        donHang.setSdt((String) request.get("sdt"));
        donHang.setTrangThai(TrangThaiDonHang.CHO_XU_LY);
        donHang.setNguoiDung(nguoiDung);

        String maGiamGia = (String) request.get("maGiamGia");
        if (maGiamGia != null && !maGiamGia.isEmpty()) {
            GiamGia giamGia = giamGiaRepository.findByMaGiamGia(maGiamGia)
                    .orElseThrow(() -> new RuntimeException("Mã giảm giá không hợp lệ"));
            donHang.setGiamGia(giamGia);
        }

        DonHang savedDonHang = donHangRepository.save(donHang);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> chiTietList = (List<Map<String, Object>>) request.get("chiTiet");
        if (chiTietList == null || chiTietList.isEmpty()) {
            throw new RuntimeException("Đơn hàng phải có ít nhất 1 món ăn");
        }

        double tongTien = 0;
        for (Map<String, Object> ct : chiTietList) {
            Long monAnId = Long.valueOf(ct.get("monAnId").toString());
            Integer soLuong = Integer.valueOf(ct.get("soLuong").toString());
            if (soLuong <= 0) throw new RuntimeException("Số lượng phải > 0");

            MonAn monAn = monAnRepository.findById(monAnId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn"));

            ChiTietDonHang chiTiet = new ChiTietDonHang();
            chiTiet.setDonHang(savedDonHang);
            chiTiet.setMonAn(monAn);
            chiTiet.setSoLuong(soLuong);
            chiTietDonHangRepository.save(chiTiet);

            tongTien += monAn.getGia() * soLuong;
        }

        // Tính giảm giá
        double tienGiamGia = 0;
        if (savedDonHang.getGiamGia() != null) {
            GiamGia gg = savedDonHang.getGiamGia();
            if (gg.isLaPhanTram()) {
                tienGiamGia = tongTien * gg.getGiaTri() / 100;
            } else {
                tienGiamGia = gg.getGiaTri();
            }
        }

        double tongThanhToan = Math.max(0, tongTien - tienGiamGia);

        ThanhToan thanhToan = new ThanhToan();
        thanhToan.setDonHang(savedDonHang);
        thanhToan.setSoTien(tongThanhToan);
        thanhToan.setPhuongThuc((String) request.get("phuongThucThanhToan"));
        thanhToan.setTrangThai("PENDING"); // giữ String
        thanhToan.setNgayThanhToan(LocalDateTime.now());
        thanhToanRepository.save(thanhToan);

        return savedDonHang;
    }

    public List<DonHang> getMyOrders() {
        NguoiDung nguoiDung = getCurrentUser();
        return donHangRepository.findByNguoiDungOrderByNgayDatDesc(nguoiDung);
    }

    public DonHang getMyOrderById(Long id) {
        NguoiDung nguoiDung = getCurrentUser();
        DonHang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        if (!donHang.getNguoiDung().getId().equals(nguoiDung.getId())) {
            throw new RuntimeException("Không có quyền xem đơn hàng này");
        }

        return donHang;
    }

    public List<DonHang> getAll(String trangThai) {
        if (trangThai != null && !trangThai.isEmpty()) {
            return donHangRepository.findByTrangThaiOrderByNgayDatDesc(TrangThaiDonHang.valueOf(trangThai));
        }
        return donHangRepository.findAllByOrderByNgayDatDesc();
    }

    @Transactional
    public DonHang updateStatus(Long id, TrangThaiDonHang trangThai) {
        DonHang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        donHang.setTrangThai(trangThai);

        // ✅ KHI ĐƠN ĐÃ GIAO → TỰ ĐỘNG COI LÀ ĐÃ THANH TOÁN (COD)
        if (trangThai == TrangThaiDonHang.DA_GIAO) {
            ThanhToan thanhToan = donHang.getThanhToan();
            if (thanhToan != null) {
                thanhToan.setTrangThai("DA_THANH_TOAN");
                thanhToan.setNgayThanhToan(LocalDateTime.now());
                thanhToanRepository.save(thanhToan);
            }
        }

        return donHangRepository.save(donHang);
    }


    public void delete(Long id) {
        donHangRepository.deleteById(id);
    }


}
