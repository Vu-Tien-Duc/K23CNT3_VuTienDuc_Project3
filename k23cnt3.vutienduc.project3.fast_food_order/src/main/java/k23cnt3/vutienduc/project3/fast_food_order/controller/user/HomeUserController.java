package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import jakarta.servlet.http.HttpSession;
import k23cnt3.vutienduc.project3.fast_food_order.entity.MonAn;
import k23cnt3.vutienduc.project3.fast_food_order.repository.MonAnRepository;
import k23cnt3.vutienduc.project3.fast_food_order.repository.NguoiDungRepository;
import k23cnt3.vutienduc.project3.fast_food_order.repository.VaiTroRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeUserController extends BaseController {

    private final MonAnRepository monAnRepository;
    private final VaiTroRepository vaiTroRepository;
    private final PasswordEncoder passwordEncoder;

    // ✅ Constructor rõ ràng – gọi super()
    public HomeUserController(
            NguoiDungRepository nguoiDungRepository,
            MonAnRepository monAnRepository,
            VaiTroRepository vaiTroRepository,
            PasswordEncoder passwordEncoder
    ) {
        super(nguoiDungRepository);
        this.monAnRepository = monAnRepository;
        this.vaiTroRepository = vaiTroRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ================= HOME =================
    @GetMapping({"/", "/index"})
    public String index(Model model, Principal principal, HttpSession session) {

        // user đăng nhập
        addLoggedUser(model, principal);

        // số lượng giỏ hàng (session cart)
        addCartCount(model, session);

        // danh sách món ăn
        List<MonAn> dsMonAn = monAnRepository.findAll();
        model.addAttribute(
                "monAnList",
                dsMonAn.subList(0, Math.min(12, dsMonAn.size()))
        );

        return "user/index";
    }

    // ================= LOGIN =================
    @GetMapping("/login")
    public String login(Model model, Principal principal, HttpSession session) {
        addLoggedUser(model, principal);
        addCartCount(model, session);
        return "login";
    }

    // ================= REGISTER =================
    @GetMapping("/register")
    public String register(Model model, Principal principal, HttpSession session) {
        addLoggedUser(model, principal);
        addCartCount(model, session);
        return "register";
    }

    @PostMapping("/register/save")
    public String saveRegister(
            @RequestParam String ten,
            @RequestParam String email,
            @RequestParam String matKhau,
            @RequestParam String sdt,
            @RequestParam String diaChi,
            Model model,
            HttpSession session
    ) {

        if (nguoiDungRepository.existsByEmail(email)) {
            model.addAttribute("error", "Email đã tồn tại!");
            addCartCount(model, session);
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

        return "redirect:/login?success.html";
    }
}
