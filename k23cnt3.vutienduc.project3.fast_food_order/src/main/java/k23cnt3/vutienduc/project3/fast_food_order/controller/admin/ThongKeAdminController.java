package k23cnt3.vutienduc.project3.fast_food_order.controller.admin;

import k23cnt3.vutienduc.project3.fast_food_order.service.ThongKeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/thong-ke")
public class ThongKeAdminController {

    private final ThongKeService thongKeService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAllAttributes(thongKeService.getDashboard());
        return "admin/thong-ke/index";
    }
}
