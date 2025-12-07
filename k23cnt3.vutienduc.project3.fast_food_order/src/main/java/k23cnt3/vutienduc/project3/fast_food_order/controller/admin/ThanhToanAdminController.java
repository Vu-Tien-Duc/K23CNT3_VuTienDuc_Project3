package k23cnt3.vutienduc.project3.fast_food_order.controller.admin;

import k23cnt3.vutienduc.project3.fast_food_order.entity.ThanhToan;
import k23cnt3.vutienduc.project3.fast_food_order.service.ThanhToanService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/thanh-toan")
@RequiredArgsConstructor
public class ThanhToanAdminController {

    private final ThanhToanService thanhToanService;

    // ===== DANH SÁCH THANH TOÁN =====
    @GetMapping
    public String list(
            @RequestParam(required = false) String phuongThuc,
            @RequestParam(required = false) String trangThai,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            Model model
    ) {
        List<ThanhToan> thanhToans = thanhToanService.filterThanhToan(phuongThuc, trangThai, fromDate);

        Double totalRevenue = thanhToanService.getTotalRevenue();
        long onlinePayments = thanhToanService.countByMethod("ONLINE");
        long codPayments = thanhToanService.countByMethod("COD");

        model.addAttribute("thanhToans", thanhToans);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("onlinePayments", onlinePayments);
        model.addAttribute("codPayments", codPayments);
        model.addAttribute("phuongThuc", phuongThuc);
        model.addAttribute("trangThai", trangThai);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("activePage", "thanh-toan");

        return "admin/thanh-toan/list";
    }

    // ===== CHI TIẾT THANH TOÁN - DÙNG QUERY CUSTOM =====
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        // ✅ Dùng query custom để tránh lazy loading
        Map<String, Object> thanhToan = thanhToanService.getThanhToanForEdit(id);

        model.addAttribute("thanhToan", thanhToan);
        model.addAttribute("activePage", "thanh-toan");

        return "admin/thanh-toan/detail";
    }

    // ===== XÓA THANH TOÁN =====
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            thanhToanService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa thanh toán #" + id + " thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa: " + e.getMessage());
        }
        return "redirect:/admin/thanh-toan";
    }
}