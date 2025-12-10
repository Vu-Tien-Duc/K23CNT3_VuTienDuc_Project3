package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import jakarta.servlet.http.HttpSession;
import k23cnt3.vutienduc.project3.fast_food_order.service.DonHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/don-hang")
@RequiredArgsConstructor
public class DonHangUserController {

    private final DonHangService donHangService;

    private void addUserSession(Model model, HttpSession session) {
        model.addAttribute("nguoiDung", session.getAttribute("nguoiDung"));
        model.addAttribute("cartCount", session.getAttribute("cartCount"));
    }

    @GetMapping
    public String myOrders(Model model, HttpSession session) {

        addUserSession(model, session);

        model.addAttribute("donHangs", donHangService.getMyOrders());
        return "user/don-hang/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, HttpSession session) {

        addUserSession(model, session);

        model.addAttribute("donHang", donHangService.getMyOrderById(id));
        return "user/don-hang/detail";
    }
}
