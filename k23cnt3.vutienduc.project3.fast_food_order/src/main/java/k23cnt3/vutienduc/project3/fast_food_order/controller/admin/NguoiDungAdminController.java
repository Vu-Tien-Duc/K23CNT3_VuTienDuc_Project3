package k23cnt3.vutienduc.project3.fast_food_order.controller.admin;

import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import k23cnt3.vutienduc.project3.fast_food_order.service.NguoiDungService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/nguoi-dung")
public class NguoiDungAdminController {

    private final NguoiDungService nguoiDungService;

    @GetMapping
    public String list(Model model) {
        List<NguoiDung> users = nguoiDungService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/nguoi-dung/list";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        nguoiDungService.deleteUser(id);
        return "redirect:/admin/nguoi-dung";
    }
}
