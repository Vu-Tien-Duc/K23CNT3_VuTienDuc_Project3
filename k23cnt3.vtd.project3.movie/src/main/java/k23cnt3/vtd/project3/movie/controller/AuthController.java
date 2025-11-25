package k23cnt3.vtd.project3.movie.controller;

import k23cnt3.vtd.project3.movie.entity.NguoiDung;
import k23cnt3.vtd.project3.movie.service.NguoiDungService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final NguoiDungService nguoiDungService;

    // Trang login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Trang đăng ký
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("nguoiDung", new NguoiDung());
        return "register";
    }

    // Xử lý đăng ký
    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute("nguoiDung") NguoiDung nguoiDung,
                                  BindingResult result,
                                  Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        // Kiểm tra tên đăng nhập hoặc email đã tồn tại
        if (nguoiDungService.findByTenDangNhap(nguoiDung.getTenDangNhap()).isPresent()) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại");
            return "register";
        }

        // Lưu user
        nguoiDungService.dangKy(nguoiDung);

        model.addAttribute("success", "Đăng ký thành công. Hãy đăng nhập!");
        return "login";
    }
}
