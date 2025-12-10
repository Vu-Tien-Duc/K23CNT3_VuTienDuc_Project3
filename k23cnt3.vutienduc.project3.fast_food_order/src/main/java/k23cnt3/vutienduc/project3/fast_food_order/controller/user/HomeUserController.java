package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import k23cnt3.vutienduc.project3.fast_food_order.entity.MonAn;
import k23cnt3.vutienduc.project3.fast_food_order.repository.MonAnRepository;
import k23cnt3.vutienduc.project3.fast_food_order.repository.NguoiDungRepository;
import k23cnt3.vutienduc.project3.fast_food_order.repository.VaiTroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeUserController {

    private final NguoiDungRepository nguoiDungRepository;
    private final MonAnRepository monAnRepository;
    private final VaiTroRepository vaiTroRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping({"/", "/index"})
    public String index(Model model, Principal principal) {

        // Lấy thông tin user đang đăng nhập (nếu có)
        if (principal != null) {
            var user = nguoiDungRepository.findByEmail(principal.getName()).orElse(null);
            model.addAttribute("nguoiDung", user);
        } else {
            model.addAttribute("nguoiDung", null);
        }

        // Lấy danh sách món ăn
        List<MonAn> dsMonAn = monAnRepository.findAll();
        model.addAttribute("monAnList", dsMonAn.subList(0, Math.min(5, dsMonAn.size())));

        return "user/index";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register/save")
    public String saveRegister(
            @RequestParam String ten,
            @RequestParam String email,
            @RequestParam String matKhau,
            @RequestParam String sdt,
            @RequestParam String diaChi,
            Model model
    ) {

        if (nguoiDungRepository.existsByEmail(email)) {
            model.addAttribute("error", "Email đã tồn tại!");
            return "register";
        }

        var userRole = vaiTroRepository.findByTenVaiTro("USER")
                .orElseThrow(() -> new RuntimeException("Role USER không tồn tại!"));

        var user = k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung.builder()
                .ten(ten)
                .email(email)
                .matKhau(passwordEncoder.encode(matKhau))
                .sdt(sdt)
                .diaChi(diaChi)
                .vaiTro(userRole)
                .build();

        nguoiDungRepository.save(user);

        return "redirect:/login?success";
    }
}
