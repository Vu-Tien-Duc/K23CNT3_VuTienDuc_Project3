package k23cnt3.vutienduc.project3.fast_food_order.controller.admin;

import k23cnt3.vutienduc.project3.fast_food_order.service.ThongKeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ThongKeAdminController {

    private final ThongKeService thongKeService;

    @GetMapping("/admin/thong-ke")
    public String thongKe(Model model) {

        // Số liệu tổng quan
        model.addAttribute("tongDonHang", thongKeService.tongDonHang());
        model.addAttribute("tongNguoiDung", thongKeService.tongNguoiDung());
        model.addAttribute("tongBinhLuan", thongKeService.tongBinhLuan());
        model.addAttribute("tongMonAn", thongKeService.tongMonAn());

        // Doanh thu
        model.addAttribute("tongDoanhThu", thongKeService.tongDoanhThu());
        model.addAttribute("doanhThuHomNay", thongKeService.doanhThuHomNay());

        // Đánh giá
        model.addAttribute("avgRating", thongKeService.diemDanhGiaTrungBinh());

        // Dữ liệu cho biểu đồ
        model.addAttribute("doanhThuTheoThang", thongKeService.doanhThuTheoThang());
        model.addAttribute("donHangTheoTrangThai", thongKeService.donHangTheoTrangThai());
        model.addAttribute("topMonAnPhoBien", thongKeService.topMonAnPhoBien());

        return "admin/thong-ke/index";
    }

}