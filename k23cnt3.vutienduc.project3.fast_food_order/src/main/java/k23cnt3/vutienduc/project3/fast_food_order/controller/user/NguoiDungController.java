package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import k23cnt3.vutienduc.project3.fast_food_order.service.NguoiDungService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
@RequestMapping("/nguoi-dung/profile")
public class NguoiDungController {

    private final NguoiDungService nguoiDungService;

    @GetMapping
    public String profile(Model model) {
        NguoiDung nguoiDung = nguoiDungService.getProfile();
        model.addAttribute("nguoiDung", nguoiDung);
        return "user/nguoi-dung/profile";
    }

    @PostMapping("/update")
    public String update(NguoiDung nguoiDung) {
        nguoiDungService.updateProfile(nguoiDung);
        return "redirect:/nguoi-dung/profile";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String matKhauCu, @RequestParam String matKhauMoi) {
        nguoiDungService.changePassword(matKhauCu, matKhauMoi);
        return "redirect:/nguoi-dung/profile";
    }
}
