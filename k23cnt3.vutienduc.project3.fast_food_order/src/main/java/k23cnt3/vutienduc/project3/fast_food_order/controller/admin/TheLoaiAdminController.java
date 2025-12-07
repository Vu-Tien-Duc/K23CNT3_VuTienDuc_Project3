package k23cnt3.vutienduc.project3.fast_food_order.controller.admin;

import k23cnt3.vutienduc.project3.fast_food_order.entity.TheLoai;
import k23cnt3.vutienduc.project3.fast_food_order.service.TheLoaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/the-loai")
@RequiredArgsConstructor
public class TheLoaiAdminController {

    private final TheLoaiService theLoaiService;

    // ====================== LIST ======================
    @GetMapping
    public String list(Model model) {
        model.addAttribute("list", theLoaiService.getAll());
        model.addAttribute("title", "Danh sách thể loại");
        model.addAttribute("headerTitle", "Thể loại");
        return "admin/the-loai/list"; // ✅ list.html
    }

    // ====================== SHOW CREATE FORM ======================
    @GetMapping("/create")
    public String create(Model model) {
        TheLoai theLoai = new TheLoai();
        model.addAttribute("theLoai", theLoai);
        model.addAttribute("action", "/admin/the-loai/create"); // URL action form
        model.addAttribute("title", "Thêm thể loại");
        model.addAttribute("headerTitle", "Thêm thể loại");
        return "admin/the-loai/form"; // ✅ form.html
    }

    @PostMapping("/create")
    public String save(@ModelAttribute TheLoai theLoai) {
        theLoaiService.create(theLoai);
        return "redirect:/admin/the-loai";
    }

    // ====================== SHOW EDIT FORM ======================
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        TheLoai theLoai = theLoaiService.getById(id);
        model.addAttribute("theLoai", theLoai);
        model.addAttribute("action", "/admin/the-loai/edit/" + id); // URL action form
        model.addAttribute("title", "Sửa thể loại");
        model.addAttribute("headerTitle", "Sửa thể loại");
        return "admin/the-loai/form"; // ✅ form.html
    }

    // ====================== UPDATE ======================
    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute TheLoai theLoai) {
        theLoaiService.update(id, theLoai);
        return "redirect:/admin/the-loai";
    }

    // ====================== DELETE ======================
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        try {
            theLoaiService.delete(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/admin/the-loai";
    }
}
