package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import k23cnt3.vutienduc.project3.fast_food_order.entity.DonHang;
import k23cnt3.vutienduc.project3.fast_food_order.service.DonHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/don-hang")
public class DonHangController {

    private final DonHangService donHangService;

    @GetMapping("/my-orders")
    public String myOrders(Model model) {
        model.addAttribute("donHangs", donHangService.getMyOrders());
        return "user/don-hang/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        DonHang donHang = donHangService.getMyOrderById(id);
        model.addAttribute("donHang", donHang);
        return "user/don-hang/detail.html";
    }

    @PostMapping("/create")
    public String create(@RequestParam Map<String, Object> request) {
        donHangService.create(request);
        return "redirect:/don-hang/my-orders";
    }
}
