package k23cnt3.vutienduc.project3.fast_food_order.controller.admin;

import k23cnt3.vutienduc.project3.fast_food_order.service.ThongKeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeAdminController {

    private final ThongKeService thongKeService;

    @GetMapping("/admin/index")
    public String index(Model model) {
        model.addAllAttributes(thongKeService.getDashboard());
        return "admin/index"; // trả về view admin/index.html
    }

    @GetMapping("/admin/login")
    public String login() {
        return "/login"; // trả về view login admin
    }
}
