package k23cnt3.vutienduc.project3.fast_food_order.controller.admin;

import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import k23cnt3.vutienduc.project3.fast_food_order.service.NguoiDungService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/nguoi-dung")
public class NguoiDungAdminController {

    private final NguoiDungService nguoiDungService;

    // ====================== LIST + SEARCH + PAGINATION ======================
    @GetMapping
    public String listUsers(
            Model model,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "ALL") String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Page<NguoiDung> users = nguoiDungService.searchUsers(keyword, role, page, size);

        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);
        model.addAttribute("role", role);
        model.addAttribute("roles", nguoiDungService.getAllRoles());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);

        return "admin/nguoi-dung/list"; // ✅ index.html
    }

    // ====================== SHOW FORM CREATE ======================
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new NguoiDung());
        model.addAttribute("roles", nguoiDungService.getAllRoles());
        model.addAttribute("action", "/admin/nguoi-dung/create"); // URL action cho form
        return "admin/nguoi-dung/form"; // ✅ form.html
    }

    // ====================== CREATE USER ======================
    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") NguoiDung user) {
        nguoiDungService.createUser(user);
        return "redirect:/admin/nguoi-dung?success=created";
    }

    // ====================== SHOW FORM EDIT ======================
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        NguoiDung user = nguoiDungService.getById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", nguoiDungService.getAllRoles());
        model.addAttribute("action", "/admin/nguoi-dung/edit"); // URL action cho form
        return "admin/nguoi-dung/form"; // ✅ form.html
    }

    // ====================== UPDATE USER ======================
    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") NguoiDung user) {
        nguoiDungService.updateUserByAdmin(user);
        return "redirect:/admin/nguoi-dung?success=updated";
    }

    // ====================== DELETE USER ======================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        nguoiDungService.deleteUser(id);
        return "redirect:/admin/nguoi-dung?success=deleted";
    }
}
