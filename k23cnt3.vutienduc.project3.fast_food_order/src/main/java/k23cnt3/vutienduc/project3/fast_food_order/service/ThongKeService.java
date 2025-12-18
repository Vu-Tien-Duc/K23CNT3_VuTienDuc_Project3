package k23cnt3.vutienduc.project3.fast_food_order.service;

import k23cnt3.vutienduc.project3.fast_food_order.repository.BinhLuanRepository;
import k23cnt3.vutienduc.project3.fast_food_order.repository.DonHangRepository;
import k23cnt3.vutienduc.project3.fast_food_order.repository.MonAnRepository;
import k23cnt3.vutienduc.project3.fast_food_order.repository.NguoiDungRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // ✅ TỔNG DOANH THU (đơn đã giao)
    public double tongDoanhThu() {
        return donHangRepository.findAll().stream()
                .filter(dh -> dh.getTrangThai() != null
                        && dh.getTrangThai().name().equals("DA_GIAO"))
                .mapToDouble(dh -> dh.getTongTien())
                .sum();
    }

    // ✅ DOANH THU HÔM NAY
    public double doanhThuHomNay() {
        LocalDate today = LocalDate.now();

        return donHangRepository.findAll().stream()
                .filter(dh ->
                        dh.getNgayDat() != null &&
                                dh.getNgayDat().toLocalDate().equals(today) &&
                                dh.getTrangThai() != null &&
                                dh.getTrangThai().name().equals("DA_GIAO")
                )
                .mapToDouble(dh -> dh.getTongTien())
                .sum();
    }

    // ✅ ĐIỂM ĐÁNH GIÁ TRUNG BÌNH
    public double diemDanhGiaTrungBinh() {
        return binhLuanRepository.findAll().stream()
                .mapToInt(bl -> bl.getDanhGia())
                .average()
                .orElse(0);
    }

    // ✅ DOANH THU THEO 12 THÁNG (năm hiện tại)
    public List<Double> doanhThuTheoThang() {
        int namHienTai = LocalDate.now().getYear();
        List<Double> result = new ArrayList<>();

        for (int thang = 1; thang <= 12; thang++) {
            LocalDate startDate = LocalDate.of(namHienTai, thang, 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

            double doanhThu = donHangRepository.findAll().stream()
                    .filter(dh -> dh.getNgayDat() != null
                            && !dh.getNgayDat().toLocalDate().isBefore(startDate)
                            && !dh.getNgayDat().toLocalDate().isAfter(endDate)
                            && dh.getTrangThai() != null
                            && dh.getTrangThai().name().equals("DA_GIAO"))
                    .mapToDouble(dh -> dh.getTongTien())
                    .sum();

            result.add(doanhThu);
        }

        return result;
    }

    // ✅ ĐẾM ĐƠN HÀNG THEO TRẠNG THÁI
    public Map<String, Long> donHangTheoTrangThai() {
        Map<String, Long> result = new HashMap<>();

        result.put("CHO_XAC_NHAN", donHangRepository.findAll().stream()
                .filter(dh -> dh.getTrangThai() != null && dh.getTrangThai().name().equals("CHO_XAC_NHAN"))
                .count());

        result.put("DANG_XU_LY", donHangRepository.findAll().stream()
                .filter(dh -> dh.getTrangThai() != null && dh.getTrangThai().name().equals("DANG_XU_LY"))
                .count());

        result.put("DANG_GIAO", donHangRepository.findAll().stream()
                .filter(dh -> dh.getTrangThai() != null && dh.getTrangThai().name().equals("DANG_GIAO"))
                .count());

        result.put("DA_GIAO", donHangRepository.findAll().stream()
                .filter(dh -> dh.getTrangThai() != null && dh.getTrangThai().name().equals("DA_GIAO"))
                .count());

        result.put("DA_HUY", donHangRepository.findAll().stream()
                .filter(dh -> dh.getTrangThai() != null && dh.getTrangThai().name().equals("DA_HUY"))
                .count());

        return result;
    }

    // ✅ TOP 5 MÓN ĂN PHỔ BIẾN (dựa trên số bình luận)
    public Map<String, Long> topMonAnPhoBien() {
        Map<String, Long> result = new HashMap<>();

        // Đếm số bình luận cho mỗi món ăn
        Map<Long, Long> countMap = new HashMap<>();
        binhLuanRepository.findAll().forEach(bl -> {
            if (bl.getMonAn() != null) {
                Long monAnId = bl.getMonAn().getId();
                countMap.put(monAnId, countMap.getOrDefault(monAnId, 0L) + 1);
            }
        });

        // Sắp xếp và lấy top 5
        countMap.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(5)
                .forEach(entry -> {
                    monAnRepository.findById(entry.getKey()).ifPresent(monAn -> {
                        result.put(monAn.getTen(), entry.getValue());
                    });
                });

        return result;
    }
}