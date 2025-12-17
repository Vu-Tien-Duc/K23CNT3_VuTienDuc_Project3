package k23cnt3.vutienduc.project3.fast_food_order.controller.admin;

import k23cnt3.vutienduc.project3.fast_food_order.entity.ThanhToan;
import k23cnt3.vutienduc.project3.fast_food_order.service.ThanhToanService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/thanh-toan")
@RequiredArgsConstructor
public class ThanhToanAdminController {

    private final ThanhToanService thanhToanService;

    // ===== DANH SÁCH =====
    @GetMapping
    public String list(
            @RequestParam(required = false) String phuongThuc,
            @RequestParam(required = false) String trangThai,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fromDate,
            Model model
    ) {
        List<ThanhToan> thanhToans =
                thanhToanService.filterThanhToan(phuongThuc, trangThai, fromDate);

        model.addAttribute("thanhToans", thanhToans);
        model.addAttribute("totalRevenue", thanhToanService.getTotalRevenue());
        model.addAttribute("onlinePayments", thanhToanService.countByMethod("ONLINE"));
        model.addAttribute("codPayments", thanhToanService.countByMethod("COD"));
        model.addAttribute("phuongThuc", phuongThuc);
        model.addAttribute("trangThai", trangThai);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("activePage", "thanh-toan");

        return "admin/thanh-toan/list";
    }

    // ===== CHI TIẾT =====
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Map<String, Object> thanhToan =
                thanhToanService.getThanhToanForEdit(id);

        model.addAttribute("thanhToan", thanhToan);
        model.addAttribute("activePage", "thanh-toan");

        return "admin/thanh-toan/detail";
    }

    // ===== XÓA =====
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            thanhToanService.deleteById(id);
            redirectAttributes.addFlashAttribute(
                    "successMessage", "Xóa thanh toán #" + id + " thành công!"
            );
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", "Không thể xóa: " + e.getMessage()
            );
        }
        return "redirect:/admin/thanh-toan";
    }
}
