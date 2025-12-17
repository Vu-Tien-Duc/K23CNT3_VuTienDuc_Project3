package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import jakarta.servlet.http.HttpSession;
import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import k23cnt3.vutienduc.project3.fast_food_order.repository.NguoiDungRepository;
import k23cnt3.vutienduc.project3.fast_food_order.service.NguoiDungService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/nguoi-dung")
public class NguoiDungUserController extends BaseController {

    private final NguoiDungService nguoiDungService;

    public NguoiDungUserController(
            NguoiDungRepository nguoiDungRepository,
            NguoiDungService nguoiDungService
    ) {
        super(nguoiDungRepository);
        this.nguoiDungService = nguoiDungService;
    }

    /**
     * Hiển thị trang thông tin cá nhân
     */
    @GetMapping("/profile")
    public String profile(
            Model model,
            Principal principal,
            HttpSession session
    ) {
        try {
            addLoggedUser(model, principal);
            addCartCount(model, session);

            NguoiDung profileUser = nguoiDungService.getProfile();

            if (profileUser == null) {
                model.addAttribute("error", "Không tìm thấy thông tin người dùng");
                return "redirect:/";
            }

            model.addAttribute("profileUser", profileUser);
            return "user/nguoi-dung/profile";

        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra khi tải thông tin cá nhân");
            return "redirect:/";
        }
    }

    /**
     * Cập nhật thông tin cá nhân
     */
    @PostMapping("/profile/update")
    public String updateProfile(
            @ModelAttribute("profileUser") NguoiDung nguoiDung,
            BindingResult result,
            RedirectAttributes ra
    ) {
        try {
            // Validate dữ liệu
            if (result.hasErrors()) {
                ra.addFlashAttribute("error", "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại!");
                return "redirect:/nguoi-dung/profile";
            }

            // Validate tên
            if (nguoiDung.getTen() == null || nguoiDung.getTen().trim().isEmpty()) {
                ra.addFlashAttribute("error", "Tên không được để trống!");
                return "redirect:/nguoi-dung/profile";
            }

            // Validate số điện thoại nếu có
            if (nguoiDung.getSdt() != null && !nguoiDung.getSdt().trim().isEmpty()) {
                String sdt = nguoiDung.getSdt().trim();
                if (!sdt.matches("^[0-9]{10,11}$")) {
                    ra.addFlashAttribute("error", "Số điện thoại không hợp lệ! (10-11 chữ số)");
                    return "redirect:/nguoi-dung/profile";
                }
            }

            // Thực hiện cập nhật
            nguoiDungService.updateProfile(nguoiDung);

            ra.addFlashAttribute("success", "✓ Cập nhật thông tin thành công!");

        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Có lỗi xảy ra khi cập nhật thông tin. Vui lòng thử lại!");
        }

        return "redirect:/nguoi-dung/profile";
    }

    /**
     * Hiển thị trang đổi mật khẩu
     */
    @GetMapping("/change-password")
    public String changePassword(
            Model model,
            Principal principal,
            HttpSession session
    ) {
        addLoggedUser(model, principal);
        addCartCount(model, session);
        return "user/nguoi-dung/change-password";
    }

    /**
     * Xử lý đổi mật khẩu
     */
    @PostMapping("/change-password")
    public String changePasswordSubmit(
            @RequestParam String oldPass,
            @RequestParam String newPass,
            @RequestParam(required = false) String confirmPass,
            RedirectAttributes ra
    ) {
        try {
            // Validate mật khẩu cũ
            if (oldPass == null || oldPass.trim().isEmpty()) {
                ra.addFlashAttribute("error", "Vui lòng nhập mật khẩu hiện tại!");
                return "redirect:/nguoi-dung/change-password";
            }

            // Validate mật khẩu mới
            if (newPass == null || newPass.trim().isEmpty()) {
                ra.addFlashAttribute("error", "Vui lòng nhập mật khẩu mới!");
                return "redirect:/nguoi-dung/change-password";
            }

            if (newPass.length() < 6) {
                ra.addFlashAttribute("error", "Mật khẩu mới phải có ít nhất 6 ký tự!");
                return "redirect:/nguoi-dung/change-password";
            }

            // Validate confirm password
            if (confirmPass != null && !newPass.equals(confirmPass)) {
                ra.addFlashAttribute("error", "Mật khẩu mới và xác nhận mật khẩu không khớp!");
                return "redirect:/nguoi-dung/change-password";
            }

            // Kiểm tra mật khẩu mới không trùng mật khẩu cũ
            if (oldPass.equals(newPass)) {
                ra.addFlashAttribute("error", "Mật khẩu mới không được trùng với mật khẩu hiện tại!");
                return "redirect:/nguoi-dung/change-password";
            }

            // Thực hiện đổi mật khẩu
            nguoiDungService.changePassword(oldPass, newPass);

            ra.addFlashAttribute("success", "✓ Đổi mật khẩu thành công! Vui lòng đăng nhập lại với mật khẩu mới.");

            // Chuyển về trang profile sau khi đổi mật khẩu thành công
            return "redirect:/nguoi-dung/profile";

        } catch (RuntimeException e) {
            // Xử lý các lỗi từ service (ví dụ: mật khẩu cũ không đúng)
            ra.addFlashAttribute("error", "✗ " + e.getMessage());
            return "redirect:/nguoi-dung/change-password";

        } catch (Exception e) {
            // Xử lý lỗi không mong muốn
            ra.addFlashAttribute("error", "Có lỗi xảy ra khi đổi mật khẩu. Vui lòng thử lại!");
            return "redirect:/nguoi-dung/change-password";
        }
    }
}