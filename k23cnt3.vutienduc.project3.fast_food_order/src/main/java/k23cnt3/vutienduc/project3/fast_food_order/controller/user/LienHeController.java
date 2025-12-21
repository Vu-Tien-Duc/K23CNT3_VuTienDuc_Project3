package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import jakarta.servlet.http.HttpSession;
import k23cnt3.vutienduc.project3.fast_food_order.repository.NguoiDungRepository;
import k23cnt3.vutienduc.project3.fast_food_order.service.MailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class LienHeController extends BaseController {

    private final MailService mailService;

    // ✅ Constructor + super()
    public LienHeController(
            NguoiDungRepository nguoiDungRepository,
            MailService mailService
    ) {
        super(nguoiDungRepository);
        this.mailService = mailService;
    }

    // ================= LIÊN HỆ =================
    @GetMapping("/lien-he")
    public String lienHeForm(
            Model model,
            Principal principal,
            HttpSession session
    ) {
        addLoggedUser(model, principal);
        addCartCount(model, session);
        return "user/lien-he";
    }

    @PostMapping("/lien-he")
    public String guiLienHe(
            @RequestParam String ten,
            @RequestParam String email,
            @RequestParam String noiDung,
            RedirectAttributes redirect
    ) {
        mailService.guiLienHe(ten, email, noiDung);
        redirect.addFlashAttribute("success", "Gửi liên hệ thành công!");
        return "redirect:/user/lien-he";
    }
}
