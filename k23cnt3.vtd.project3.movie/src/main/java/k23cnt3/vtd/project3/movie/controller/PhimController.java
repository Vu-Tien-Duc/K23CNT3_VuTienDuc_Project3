package k23cnt3.vtd.project3.movie.controller;

import k23cnt3.vtd.project3.movie.entity.BinhLuan;
import k23cnt3.vtd.project3.movie.entity.Phim;
import k23cnt3.vtd.project3.movie.service.BinhLuanService;
import k23cnt3.vtd.project3.movie.service.NguoiDungService;
import k23cnt3.vtd.project3.movie.service.PhimService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/phim")
public class PhimController {

    private final PhimService phimService;
    private final BinhLuanService binhLuanService;
    private final NguoiDungService nguoiDungService;

    /**
     * Chi tiết phim + bình luận
     */
    @GetMapping("/{id}")
    public String chiTietPhim(@PathVariable Long id, Model model) {
        Phim phim = phimService.findById(id)
                .orElseThrow(() -> new RuntimeException("Phim không tồn tại"));

        // Tính trung bình đánh giá
        Double avgRating = binhLuanService.getAverageRatingByPhim(phim.getId());
        phim.setAvgRating(avgRating != null ? avgRating : 0.0);

        List<BinhLuan> binhLuans = binhLuanService.getBinhLuanByPhim(phim);

        model.addAttribute("phim", phim);
        model.addAttribute("binhLuans", binhLuans);
        model.addAttribute("binhLuanMoi", new BinhLuan());

        return "phim/detail"; // templates/phim/detail.html
    }

    /**
     * Thêm bình luận + đánh giá
     */
    @PostMapping("/{id}/binh-luan")
    public String addBinhLuan(@PathVariable Long id,
                              @ModelAttribute("binhLuanMoi") BinhLuan binhLuan,
                              @RequestParam String tenDangNhap) {
        Phim phim = phimService.findById(id)
                .orElseThrow(() -> new RuntimeException("Phim không tồn tại"));

        binhLuan.setPhim(phim);

        binhLuan.setNguoiDung(nguoiDungService.findByTenDangNhap(tenDangNhap)
                .orElseThrow(() -> new RuntimeException("User không tồn tại")));

        binhLuanService.addBinhLuan(binhLuan);

        return "redirect:/phim/" + id;
    }
}
