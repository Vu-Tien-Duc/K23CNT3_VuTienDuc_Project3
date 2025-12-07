package k23cnt3.vutienduc.project3.fast_food_order.service;

import k23cnt3.vutienduc.project3.fast_food_order.entity.TrangThaiDonHang;
import k23cnt3.vutienduc.project3.fast_food_order.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ThongKeService {

    private final DonHangRepository donHangRepository;
    private final NguoiDungRepository nguoiDungRepository;
    private final MonAnRepository monAnRepository;
    private final ThanhToanRepository thanhToanRepository;

    public Map<String, Object> getDashboard() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("tongDonHang", donHangRepository.count());
        stats.put("choXuLy", donHangRepository.countByTrangThai(TrangThaiDonHang.CHO_XU_LY));
        stats.put("dangXuLy", donHangRepository.countByTrangThai(TrangThaiDonHang.DANG_XU_LY));
        stats.put("dangGiao", donHangRepository.countByTrangThai(TrangThaiDonHang.DANG_GIAO));
        stats.put("daGiao", donHangRepository.countByTrangThai(TrangThaiDonHang.DA_GIAO));




        stats.put("tongNguoiDung", nguoiDungRepository.count());
        stats.put("tongMonAn", monAnRepository.count());

        return stats;
    }
}