package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import jakarta.servlet.http.HttpSession;
import k23cnt3.vutienduc.project3.fast_food_order.service.MonAnService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/gio-hang")
@RequiredArgsConstructor
public class GioHangUserController {

    private final MonAnService monAnService;

    @GetMapping
    public String viewCart() {
        return "user/gio-hang/index";
    }

    @PostMapping("/add")
    public String add(@RequestParam Long id,
                      @RequestParam(defaultValue = "1") Integer soLuong,
                      HttpSession session) {

        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("CART");
        if (cart == null) cart = new HashMap<>();

        cart.put(id, cart.getOrDefault(id, 0) + soLuong);
        session.setAttribute("CART", cart);

        return "redirect:/gio-hang";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam Long id, HttpSession session) {
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("CART");
        if (cart != null) cart.remove(id);
        return "redirect:/gio-hang";
    }
}
