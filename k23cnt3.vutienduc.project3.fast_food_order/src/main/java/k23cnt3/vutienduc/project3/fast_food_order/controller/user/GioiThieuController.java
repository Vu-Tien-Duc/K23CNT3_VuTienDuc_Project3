package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import jakarta.servlet.http.HttpSession;
import k23cnt3.vutienduc.project3.fast_food_order.repository.NguoiDungRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class GioiThieuController extends BaseController {

    // ✅ Constructor + super()
    public GioiThieuController(NguoiDungRepository nguoiDungRepository) {
        super(nguoiDungRepository);
    }

    // ================= GIỚI THIỆU =================
    @GetMapping("/gioi-thieu")
    public String gioiThieu(
            Model model,
            Principal principal,
            HttpSession session
    ) {
        addLoggedUser(model, principal);
        addCartCount(model, session);
        return "user/gioi-thieu";
    }
}
