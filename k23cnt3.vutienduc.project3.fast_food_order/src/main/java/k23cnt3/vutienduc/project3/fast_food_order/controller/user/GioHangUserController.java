package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import jakarta.servlet.http.HttpSession;
import k23cnt3.vutienduc.project3.fast_food_order.entity.MonAn;
import k23cnt3.vutienduc.project3.fast_food_order.repository.NguoiDungRepository;
import k23cnt3.vutienduc.project3.fast_food_order.service.MonAnService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class GioHangUserController extends BaseController {

    private final MonAnService monAnService;

    // ✅ Constructor + super()
    public GioHangUserController(
            NguoiDungRepository nguoiDungRepository,
            MonAnService monAnService
    ) {
        super(nguoiDungRepository);
        this.monAnService = monAnService;
    }

    // ================== XEM GIỎ HÀNG ==================
    @GetMapping
    public String viewCart(
            HttpSession session,
            Model model,
            Principal principal
    ) {

        // 🔥 DÙNG BASE
        addLoggedUser(model, principal);
        addCartCount(model, session);

        Map<Long, Integer> cart =
                (Map<Long, Integer>) session.getAttribute("CART");

        Map<MonAn, Integer> cartItems = new HashMap<>();
        double tongTien = 0;

        if (cart != null) {
            for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
                Optional<MonAn> opt = monAnService.findById(entry.getKey());
                if (opt.isPresent()) {
                    MonAn monAn = opt.get();
                    int soLuong = entry.getValue();

                    cartItems.put(monAn, soLuong);
                    tongTien += monAn.getGia() * soLuong;
                }
            }
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("tongTien", tongTien);

        return "user/gio-hang/index";
    }

    // ================== THÊM VÀO GIỎ ==================
    @GetMapping("/add/{id}")
    public String add(
            @PathVariable Long id,
            HttpSession session
    ) {

        Map<Long, Integer> cart =
                (Map<Long, Integer>) session.getAttribute("CART");

        if (cart == null) {
            cart = new HashMap<>();
        }

        cart.put(id, cart.getOrDefault(id, 0) + 1);
        session.setAttribute("CART", cart);

        return "redirect:/cart";
    }

    // ================== XÓA 1 MÓN ==================
    @GetMapping("/remove/{id}")
    public String remove(
            @PathVariable Long id,
            HttpSession session
    ) {

        Map<Long, Integer> cart =
                (Map<Long, Integer>) session.getAttribute("CART");

        if (cart != null) {
            cart.remove(id);
        }

        return "redirect:/cart";
    }

    // ================== AJAX + ==================
    @PostMapping("/increase")
    @ResponseBody
    public Map<String, Object> increase(
            @RequestParam Long id,
            HttpSession session
    ) {

        Map<Long, Integer> cart =
                (Map<Long, Integer>) session.getAttribute("CART");

        if (cart != null) {
            cart.put(id, cart.get(id) + 1);
        }

        session.setAttribute("CART", cart);
        return buildResponse(cart);
    }

    // ================== AJAX − ==================
    @PostMapping("/decrease")
    @ResponseBody
    public Map<String, Object> decrease(
            @RequestParam Long id,
            HttpSession session
    ) {

        Map<Long, Integer> cart =
                (Map<Long, Integer>) session.getAttribute("CART");

        if (cart != null) {
            int qty = cart.get(id) - 1;
            if (qty <= 0) cart.remove(id);
            else cart.put(id, qty);
        }

        session.setAttribute("CART", cart);
        return buildResponse(cart);
    }

    // ================== XÓA TẤT CẢ ==================
    @GetMapping("/clear")
    public String clear(HttpSession session) {
        session.removeAttribute("CART");
        return "redirect:/cart";
    }

    // ================== RESPONSE AJAX ==================
    private Map<String, Object> buildResponse(Map<Long, Integer> cart) {

        Map<String, Object> res = new HashMap<>();
        double tongTien = 0;
        int cartCount = 0;

        if (cart != null) {
            for (Map.Entry<Long, Integer> e : cart.entrySet()) {
                MonAn m = monAnService.getById(e.getKey());
                tongTien += m.getGia() * e.getValue();
                cartCount += e.getValue();
            }
        }

        res.put("cart", cart);
        res.put("tongTien", tongTien);
        res.put("cartCount", cartCount);

        return res;
    }
}
