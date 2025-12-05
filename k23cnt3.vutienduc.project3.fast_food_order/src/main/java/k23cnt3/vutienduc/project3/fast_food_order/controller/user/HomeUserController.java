package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import k23cnt3.vutienduc.project3.fast_food_order.entity.MonAn;
import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import k23cnt3.vutienduc.project3.fast_food_order.entity.VaiTro;
import k23cnt3.vutienduc.project3.fast_food_order.repository.MonAnRepository;
import k23cnt3.vutienduc.project3.fast_food_order.repository.NguoiDungRepository;
import k23cnt3.vutienduc.project3.fast_food_order.repository.VaiTroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @GetMapping("/register")
    public String register() {
        return "register"; // trang đăng ký
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
        // Kiểm tra email đã tồn tại chưa
        if (nguoiDungRepository.existsByEmail(email)) {
            model.addAttribute("error", "Email đã tồn tại!");
            return "register";
        }

        // Lấy role USER
        VaiTro userRole = vaiTroRepository.findByTenVaiTro("USER")
                .orElseThrow(() -> new RuntimeException("Role USER không tồn tại!"));

        // Tạo user mới
        NguoiDung user = NguoiDung.builder()
                .ten(ten)
                .email(email)
                .matKhau(passwordEncoder.encode(matKhau))
                .sdt(sdt)
                .diaChi(diaChi)
                .vaiTro(userRole)
                .build();

        nguoiDungRepository.save(user);

        // Chuyển về login
        return "redirect:/login?success";
    }

}
