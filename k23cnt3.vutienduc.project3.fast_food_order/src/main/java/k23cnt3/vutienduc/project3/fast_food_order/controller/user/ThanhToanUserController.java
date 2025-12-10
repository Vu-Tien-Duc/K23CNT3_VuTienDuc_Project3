package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import jakarta.servlet.http.HttpSession;
import k23cnt3.vutienduc.project3.fast_food_order.entity.DonHang;
import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import k23cnt3.vutienduc.project3.fast_food_order.service.DonHangService;
import k23cnt3.vutienduc.project3.fast_food_order.service.MonAnService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/thanh-toan")
@RequiredArgsConstructor
public class ThanhToanUserController {

    private final DonHangService donHangService;
    private final MonAnService monAnService;

    private void addUserSession(Model model, HttpSession session) {
        model.addAttribute("nguoiDung", session.getAttribute("nguoiDung"));
        model.addAttribute("cartCount", session.getAttribute("cartCount"));
    }

    @GetMapping
    public String checkout(HttpSession session, Model model) {

        addUserSession(model, session);

        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("CART");
        model.addAttribute("cart", cart);

        return "user/thanh-toan/index";
    }

    @PostMapping
    public String createOrder(@RequestParam Map<String, String> params,
                              HttpSession session) {

        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("CART");

        if (cart == null || cart.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống");
        }

        List<Map<String, Object>> chiTiet = new ArrayList<>();
        cart.forEach((monAnId, soLuong) ->
                chiTiet.add(Map.of("monAnId", monAnId, "soLuong", soLuong))
        );

        Map<String, Object> request = Map.of(
                "diaChiGiao", params.get("diaChiGiao"),
                "sdt", params.get("sdt"),
                "maGiamGia", params.get("maGiamGia"),
                "phuongThucThanhToan", params.get("phuongThucThanhToan"),
                "chiTiet", chiTiet
        );

        DonHang donHang = donHangService.create(request);

        session.removeAttribute("CART");
        session.setAttribute("cartCount", 0);

        return "redirect:/don-hang/" + donHang.getId();
    }
}
