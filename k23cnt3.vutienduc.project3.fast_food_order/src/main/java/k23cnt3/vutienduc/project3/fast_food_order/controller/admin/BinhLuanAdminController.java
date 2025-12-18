package k23cnt3.vutienduc.project3.fast_food_order.controller.admin;

import k23cnt3.vutienduc.project3.fast_food_order.entity.BinhLuan;
import k23cnt3.vutienduc.project3.fast_food_order.service.BinhLuanService;
import k23cnt3.vutienduc.project3.fast_food_order.service.MonAnService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/binh-luan")
@RequiredArgsConstructor
public class BinhLuanAdminController {

    private final BinhLuanService binhLuanService;
    private final MonAnService monAnService;

    // ===== DANH SÁCH + FILTER =====
    @GetMapping
    public String list(
            @RequestParam(required = false) Long monAnId,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) String keyword,
            Model model
    ) {
        List<BinhLuan> binhLuans = binhLuanService.filterBinhLuan(monAnId, rating, keyword);
        Double avgRating = binhLuanService.getAverageRating();

        model.addAttribute("binhLuans", binhLuans);
        model.addAttribute("avgRating", avgRating != null ? avgRating : 0.0);
        model.addAttribute("monAns", monAnService.findAll());

        model.addAttribute("monAnId", monAnId);
        model.addAttribute("rating", rating);
        model.addAttribute("keyword", keyword);

        return "admin/binh-luan/list";
    }

    // ===== CHI TIẾT =====
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        BinhLuan binhLuan = binhLuanService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bình luận"));

        model.addAttribute("binhLuan", binhLuan);
        return "admin/binh-luan/detail";
    }

    // ===== XÓA =====
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        binhLuanService.deleteById(id);
        return "redirect:/admin/binh-luan?success=deleted";
    }
}
