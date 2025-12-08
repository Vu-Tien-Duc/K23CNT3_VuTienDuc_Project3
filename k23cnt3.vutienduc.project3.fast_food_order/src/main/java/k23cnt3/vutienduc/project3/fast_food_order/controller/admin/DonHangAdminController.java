package k23cnt3.vutienduc.project3.fast_food_order.controller.admin;

import k23cnt3.vutienduc.project3.fast_food_order.entity.DonHang;
import k23cnt3.vutienduc.project3.fast_food_order.entity.TrangThaiDonHang;
import k23cnt3.vutienduc.project3.fast_food_order.service.DonHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/don-hang")
public class DonHangAdminController {

    private final DonHangService donHangService;

    // ====================== LIST ALL ORDERS ======================
    @GetMapping
    public String listOrders(Model model,
                             @RequestParam(defaultValue = "") String keyword,
                             @RequestParam(defaultValue = "ALL") String trangThai) {

        List<DonHang> orders;

        // --- Lọc theo trạng thái ---
        if (trangThai.equals("ALL") || trangThai.isEmpty()) {
            orders = donHangService.getAll(null); // lấy tất cả đơn
        } else {
            orders = donHangService.getAll(trangThai); // lọc theo enum
        }

        model.addAttribute("orders", orders);
        model.addAttribute("trangThais", TrangThaiDonHang.values()); // list enum
        model.addAttribute("trangThai", trangThai);
        model.addAttribute("keyword", keyword);
        model.addAttribute("activePage", "don-hang");

        return "admin/don-hang/list";
    }



    // ====================== VIEW ORDER DETAIL ======================
    @GetMapping("/detail/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        DonHang order = donHangService.getAll(null).stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        model.addAttribute("order", order);
        model.addAttribute("trangThais", TrangThaiDonHang.values());
        model.addAttribute("activePage", "don-hang");

        return "admin/don-hang/detail"; // ✅ detail.html
    }

    // ====================== SHOW FORM EDIT STATUS ======================
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        DonHang order = donHangService.getAll(null).stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        model.addAttribute("order", order);
        model.addAttribute("trangThais", TrangThaiDonHang.values());
        model.addAttribute("activePage", "don-hang");

        return "admin/don-hang/form"; // ✅ form.html
    }

    // ====================== UPDATE ORDER STATUS ======================
    @PostMapping("/edit")
    public String updateOrder(@ModelAttribute("order") DonHang order) {
        donHangService.updateStatus(order.getId(), order.getTrangThai());
        return "redirect:/admin/don-hang?success=updated";
    }

    // ====================== DELETE ORDER ======================
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        donHangService.delete(id);
        return "redirect:/admin/don-hang?success=deleted";
    }
}
