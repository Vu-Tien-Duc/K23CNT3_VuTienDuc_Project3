package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import k23cnt3.vutienduc.project3.fast_food_order.entity.TheLoai;
import k23cnt3.vutienduc.project3.fast_food_order.service.TheLoaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/the-loai")
public class TheLoaiController {

    private final TheLoaiService theLoaiService;

    @GetMapping
    public String list(Model model) {
        List<TheLoai> ds = theLoaiService.getAll();
        model.addAttribute("theLoais", ds);
        return "user/the-loai/list";
    }
}
