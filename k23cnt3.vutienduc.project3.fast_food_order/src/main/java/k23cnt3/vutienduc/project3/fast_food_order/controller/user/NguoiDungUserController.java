package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import jakarta.servlet.http.HttpSession;
import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import k23cnt3.vutienduc.project3.fast_food_order.repository.NguoiDungRepository;
import k23cnt3.vutienduc.project3.fast_food_order.service.NguoiDungService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/nguoi-dung")
public class NguoiDungUserController extends BaseController {

    private final NguoiDungService nguoiDungService;

    public NguoiDungUserController(
            NguoiDungRepository nguoiDungRepository,
            NguoiDungService nguoiDungService
    ) {
        super(nguoiDungRepository);
        this.nguoiDungService = nguoiDungService;
    }

    @GetMapping("/profile")
    public String profile(
            Model model,
            Principal principal,
            HttpSession session
    ) {

        addLoggedUser(model, principal);
        addCartCount(model, session);

        model.addAttribute("profileUser", nguoiDungService.getProfile());
        return "user/nguoi-dung/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(
            @ModelAttribute("profileUser") NguoiDung nguoiDung,
            RedirectAttributes ra
    ) {
        nguoiDungService.updateProfile(nguoiDung);
        ra.addFlashAttribute("success", "Cập nhật thành công");
        return "redirect:/nguoi-dung/profile";
    }

    @GetMapping("/change-password")
    public String changePassword(
            Model model,
            Principal principal,
            HttpSession session
    ) {
        addLoggedUser(model, principal);
        addCartCount(model, session);
        return "user/nguoi-dung/change-password";
    }

    @PostMapping("/change-password")
    public String changePasswordSubmit(
            @RequestParam String oldPass,
            @RequestParam String newPass,
            RedirectAttributes ra
    ) {

        try {
            nguoiDungService.changePassword(oldPass, newPass);
            ra.addFlashAttribute("success", "Đổi mật khẩu thành công");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/nguoi-dung/change-password";
        }

        return "redirect:/nguoi-dung/profile";
    }
}
