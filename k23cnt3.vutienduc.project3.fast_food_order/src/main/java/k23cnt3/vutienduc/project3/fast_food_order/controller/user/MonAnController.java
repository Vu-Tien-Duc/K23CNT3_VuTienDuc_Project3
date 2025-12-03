package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import k23cnt3.vutienduc.project3.fast_food_order.entity.MonAn;
import k23cnt3.vutienduc.project3.fast_food_order.service.MonAnService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mon-an")
public class MonAnController {

    private final MonAnService monAnService;

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(required = false) String search,
                       @RequestParam(required = false) Long theLoaiId,
                       Model model) {
        Page<MonAn> monAns = monAnService.getAll(page, size, search, theLoaiId);
        model.addAttribute("monAns", monAns);
        return "user/mon-an/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        MonAn monAn = monAnService.getById(id);
        model.addAttribute("monAn", monAn);
        return "user/mon-an/detail";
    }
}
