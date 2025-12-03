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

    @GetMapping
    public String list(@RequestParam(required = false) String trangThai, Model model) {
        List<DonHang> donHangs = donHangService.getAll(trangThai);
        model.addAttribute("donHangs", donHangs);
        return "admin/don-hang/list";
    }

    @PostMapping("/update-status/{id}")
    public String updateStatus(@PathVariable Long id, @RequestParam TrangThaiDonHang trangThai) {
        donHangService.updateStatus(id, trangThai);
        return "redirect:/admin/don-hang";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        donHangService.delete(id);
        return "redirect:/admin/don-hang";
    }
}
