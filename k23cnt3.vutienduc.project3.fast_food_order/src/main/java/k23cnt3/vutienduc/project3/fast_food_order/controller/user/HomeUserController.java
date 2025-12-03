package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import k23cnt3.vutienduc.project3.fast_food_order.entity.MonAn;
import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import k23cnt3.vutienduc.project3.fast_food_order.repository.MonAnRepository;
import k23cnt3.vutienduc.project3.fast_food_order.repository.NguoiDungRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeUserController {

    private final NguoiDungRepository nguoiDungRepository;
    private final MonAnRepository monAnRepository;

    @GetMapping({"/", "/index"})
    public String index(Model model, Principal principal) {
        NguoiDung nguoiDung = null;
        if (principal != null) {
            nguoiDung = nguoiDungRepository.findByEmail(principal.getName()).orElse(null);
        }
        model.addAttribute("nguoiDung", nguoiDung);

        List<MonAn> dsMonAn = monAnRepository.findAll();
        int count = dsMonAn.size();
        model.addAttribute("monAnList", dsMonAn.subList(0, Math.min(5, count)));

        return "user/index";
    }


    @GetMapping("/login")
    public String login() {
        return "login"; // templates/login.html
    }
}
