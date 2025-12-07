package k23cnt3.vutienduc.project3.fast_food_order.controller.admin;

import k23cnt3.vutienduc.project3.fast_food_order.entity.GiamGia;
import k23cnt3.vutienduc.project3.fast_food_order.service.GiamGiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/giam-gia")
public class GiamGiaAdminController {

    private final GiamGiaService giamGiaService;

    // ====================== LIST ALL ======================
    @GetMapping
    public String listGiamGia(Model model) {
        List<GiamGia> giamGias = giamGiaService.getAll();
        model.addAttribute("giamGias", giamGias);
        return "admin/giam-gia/list"; // ✅ list.html
    }

    // ====================== SHOW CREATE FORM ======================
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("giamGia", new GiamGia());
        return "admin/giam-gia/form"; // ✅ form.html
    }

    // ====================== CREATE ======================
    @PostMapping("/create")
    public String createGiamGia(@ModelAttribute("giamGia") GiamGia giamGia) {
        giamGiaService.create(giamGia);
        return "redirect:/admin/giam-gia?success=created";
    }

    // ====================== SHOW EDIT FORM ======================
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        GiamGia giamGia = giamGiaService.getAll().stream()
                .filter(g -> g.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mã giảm giá"));
        model.addAttribute("giamGia", giamGia);
        return "admin/giam-gia/form"; // ✅ form.html
    }

    // ====================== UPDATE ======================
    @PostMapping("/edit")
    public String updateGiamGia(@ModelAttribute("giamGia") GiamGia giamGia) {
        GiamGia existing = giamGiaService.getAll().stream()
                .filter(g -> g.getId().equals(giamGia.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mã giảm giá"));

        // Giữ nguyên ngày nếu rỗng
        if (giamGia.getNgayBatDau() == null) giamGia.setNgayBatDau(existing.getNgayBatDau());
        if (giamGia.getNgayKetThuc() == null) giamGia.setNgayKetThuc(existing.getNgayKetThuc());

        giamGiaService.update(giamGia.getId(), giamGia);
        return "redirect:/admin/giam-gia?success=updated";
    }

    // ====================== DELETE ======================
    @GetMapping("/delete/{id}")
    public String deleteGiamGia(@PathVariable Long id) {
        giamGiaService.delete(id);
        return "redirect:/admin/giam-gia?success=deleted";
    }
}
