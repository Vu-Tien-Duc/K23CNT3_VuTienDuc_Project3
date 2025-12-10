package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import jakarta.servlet.http.HttpSession;
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

    private void addUserSession(Model model, HttpSession session) {
        model.addAttribute("nguoiDung", session.getAttribute("nguoiDung"));
        model.addAttribute("cartCount", session.getAttribute("cartCount"));
    }

    @GetMapping
    public String list(Model model, HttpSession session) {

        addUserSession(model, session);

        List<TheLoai> ds = theLoaiService.getAll();
        model.addAttribute("theLoais", ds);

        return "user/the-loai/list";
    }
}
