package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import jakarta.servlet.http.HttpSession;
import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import k23cnt3.vutienduc.project3.fast_food_order.repository.NguoiDungRepository;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Map;

public abstract class BaseController {

    protected final NguoiDungRepository nguoiDungRepository;

    protected BaseController(NguoiDungRepository nguoiDungRepository) {
        this.nguoiDungRepository = nguoiDungRepository;
    }

    protected void addLoggedUser(Model model, Principal principal) {
        if (principal != null) {
            NguoiDung user = nguoiDungRepository
                    .findByEmail(principal.getName())
                    .orElse(null);
            model.addAttribute("nguoiDung", user);
        } else {
            model.addAttribute("nguoiDung", null);
        }
    }

    protected void addCartCount(Model model, HttpSession session) {
        Map<Long, Integer> cart =
                (Map<Long, Integer>) session.getAttribute("CART");

        int cartCount = cart != null
                ? cart.values().stream().mapToInt(Integer::intValue).sum()
                : 0;

        model.addAttribute("cartCount", cartCount);
    }
}
