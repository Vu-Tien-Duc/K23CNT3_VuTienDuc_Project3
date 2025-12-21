package k23cnt3.vutienduc.project3.fast_food_order.service;

import k23cnt3.vutienduc.project3.fast_food_order.entity.TrangThaiDonHang;
import k23cnt3.vutienduc.project3.fast_food_order.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ThongKeService {

    private final DonHangRepository donHangRepository;
    private final NguoiDungRepository nguoiDungRepository;
    private final BinhLuanRepository binhLuanRepository;
    private final MonAnRepository monAnRepository;

    public long tongDonHang() {
        return donHangRepository.count();
    }

    public long tongNguoiDung() {
        return nguoiDungRepository.count();
    }

    public long tongBinhLuan() {
        return binhLuanRepository.count();
    }

    public long tongMonAn() {
        return monAnRepository.count();
    }

    // ===================== DOANH THU =====================

    // ✅ TỔNG DOANH THU (ĐÃ TRỪ GIẢM GIÁ)
    public double tongDoanhThu() {
        return donHangRepository.findAll().stream()
                .filter(dh ->
                        dh.getTrangThai() == TrangThaiDonHang.DA_GIAO &&
                                dh.getThanhToan() != null
                )
                .mapToDouble(dh -> dh.getThanhToan().getSoTien())
                .sum();
    }

    // ✅ DOANH THU HÔM NAY
    public double doanhThuHomNay() {
        LocalDate today = LocalDate.now();

        return donHangRepository.findAll().stream()
                .filter(dh ->
                        dh.getNgayDat() != null &&
                                dh.getNgayDat().toLocalDate().equals(today) &&
                                dh.getTrangThai() == TrangThaiDonHang.DA_GIAO &&
                                dh.getThanhToan() != null
                )
                .mapToDouble(dh -> dh.getThanhToan().getSoTien())
                .sum();
    }

    // ✅ DOANH THU THEO 12 THÁNG (NĂM HIỆN TẠI)
    public List<Double> doanhThuTheoThang() {
        int nam = LocalDate.now().getYear();
        List<Double> result = new ArrayList<>();

        for (int thang = 1; thang <= 12; thang++) {
            int finalThang = thang;

            double doanhThu = donHangRepository.findAll().stream()
                    .filter(dh ->
                            dh.getNgayDat() != null &&
                                    dh.getNgayDat().getYear() == nam &&
                                    dh.getNgayDat().getMonthValue() == finalThang &&
                                    dh.getTrangThai() == TrangThaiDonHang.DA_GIAO &&
                                    dh.getThanhToan() != null
                    )
                    .mapToDouble(dh -> dh.getThanhToan().getSoTien())
                    .sum();

            result.add(doanhThu);
        }

        return result;
    }

    // ===================== KHÁC =====================

    // ✅ ĐIỂM ĐÁNH GIÁ TRUNG BÌNH
    public double diemDanhGiaTrungBinh() {
        return binhLuanRepository.findAll().stream()
                .mapToInt(bl -> bl.getDanhGia())
                .average()
                .orElse(0);
    }

    // ✅ ĐẾM ĐƠN HÀNG THEO TRẠNG THÁI
    public Map<String, Long> donHangTheoTrangThai() {
        Map<String, Long> result = new HashMap<>();

        for (TrangThaiDonHang tt : TrangThaiDonHang.values()) {
            long count = donHangRepository.findAll().stream()
                    .filter(dh -> dh.getTrangThai() == tt)
                    .count();
            result.put(tt.name(), count);
        }

        return result;
    }

    // ✅ TOP 5 MÓN ĂN PHỔ BIẾN (THEO SỐ BÌNH LUẬN)
    public Map<String, Long> topMonAnPhoBien() {
        Map<Long, Long> countMap = new HashMap<>();

        binhLuanRepository.findAll().forEach(bl -> {
            if (bl.getMonAn() != null) {
                Long id = bl.getMonAn().getId();
                countMap.put(id, countMap.getOrDefault(id, 0L) + 1);
            }
        });

        Map<String, Long> result = new LinkedHashMap<>();

        countMap.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(5)
                .forEach(e ->
                        monAnRepository.findById(e.getKey())
                                .ifPresent(monAn ->
                                        result.put(monAn.getTen(), e.getValue())
                                )
                );

        return result;
    }
}
