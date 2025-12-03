package k23cnt3.vutienduc.project3.fast_food_order.controller.admin;

import k23cnt3.vutienduc.project3.fast_food_order.entity.GiamGia;
import k23cnt3.vutienduc.project3.fast_food_order.service.GiamGiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/giam-gia")
public class GiamGiaAdminController {

    private final GiamGiaService giamGiaService;

    @GetMapping
    public String list(Model model) {
        List<GiamGia> ds = giamGiaService.getAll();
        model.addAttribute("giamGias", ds);
        return "admin/giam-gia/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("giamGia", new GiamGia());
        return "admin/giam-gia/form";
    }

    @PostMapping("/create")
    public String create(GiamGia giamGia) {
        giamGiaService.create(giamGia);
        return "redirect:/admin/giam-gia";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        GiamGia g = giamGiaService.getAll().stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElseThrow();
        model.addAttribute("giamGia", g);
        return "admin/giam-gia/form";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, GiamGia giamGia) {
        giamGiaService.update(id, giamGia);
        return "redirect:/admin/giam-gia";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        giamGiaService.delete(id);
        return "redirect:/admin/giam-gia";
    }
}
