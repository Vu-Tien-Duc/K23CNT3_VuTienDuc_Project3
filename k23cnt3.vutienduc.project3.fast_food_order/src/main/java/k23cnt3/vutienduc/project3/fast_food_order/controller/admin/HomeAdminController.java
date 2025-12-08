package k23cnt3.vutienduc.project3.fast_food_order.controller.admin;

import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import k23cnt3.vutienduc.project3.fast_food_order.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeAdminController {

    private final NguoiDungService nguoiDungService;
    private final DonHangService donHangService;
    private final BinhLuanService binhLuanService;
    private final MonAnService monAnService;
    private final TheLoaiService theLoaiService;
    private final GiamGiaService giamGiaService;

    @GetMapping("/admin/index")
    public String index(Model model) {
        // Thống kê
        model.addAttribute("tongNguoiDung", nguoiDungService.countAll());
        model.addAttribute("tongDonHang", donHangService.countAll());
        model.addAttribute("tongBinhLuan", binhLuanService.countAll());
        model.addAttribute("tongMonAn", monAnService.countAll());
        model.addAttribute("tongTheLoai", theLoaiService.countAll());
        model.addAttribute("tongGiamGia", giamGiaService.countAll());
        return "admin/index";
    }

    // ====================== PROFILE ADMIN ======================
    @GetMapping("/admin/profile")
    public String profile(Model model) {
        NguoiDung admin = nguoiDungService.getProfile(); // lấy admin hiện tại
        model.addAttribute("admin", admin);
        return "admin/profile"; // templates/admin/profile.html
    }

}
