package k23cnt3.vtd.project3.movie.controller;

import k23cnt3.vtd.project3.movie.entity.Phim;
import k23cnt3.vtd.project3.movie.entity.TheLoai;
import k23cnt3.vtd.project3.movie.service.PhimService;
import k23cnt3.vtd.project3.movie.service.TheLoaiService;
import k23cnt3.vtd.project3.movie.service.NguoiDungService;
import k23cnt3.vtd.project3.movie.service.BinhLuanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashSet;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PhimService phimService;
    private final TheLoaiService theLoaiService;
    private final NguoiDungService nguoiDungService;
    private final BinhLuanService binhLuanService;

    // Dashboard
    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("adminName", "Admin"); // Lấy từ SecurityContext nếu muốn
        model.addAttribute("phimCount", phimService.findAll().size());
        model.addAttribute("theLoaiCount", theLoaiService.findAll().size());
        model.addAttribute("nguoiDungCount", nguoiDungService.findAll().size());
        model.addAttribute("binhLuanCount", binhLuanService.findAll().size());
        return "admin/dashboard";
    }

    // ===== Quản lý Phim =====
    @GetMapping("/phim/new")
    public String addPhimForm(Model model) {
        model.addAttribute("phim", new Phim());
        model.addAttribute("theLoais", theLoaiService.findAll());
        return "admin/phim_form";
    }

    @PostMapping("/phim/save")
    public String savePhim(@ModelAttribute Phim phim,
                           @RequestParam(required = false) List<Long> theLoaiIds) {
        if (theLoaiIds != null && !theLoaiIds.isEmpty()) {
            List<TheLoai> selected = theLoaiService.findAll().stream()
                    .filter(t -> theLoaiIds.contains(t.getId()))
                    .toList();
            phim.setTheLoais(new HashSet<>(selected));
        }
        phimService.save(phim);
        return "redirect:/admin";
    }

    @GetMapping("/phim/edit/{id}")
    public String editPhimForm(@PathVariable Long id, Model model) {
        Phim phim = phimService.findById(id)
                .orElseThrow(() -> new RuntimeException("Phim không tồn tại"));
        model.addAttribute("phim", phim);
        model.addAttribute("theLoais", theLoaiService.findAll());
        return "admin/phim_form";
    }

    @GetMapping("/phim/delete/{id}")
    public String deletePhim(@PathVariable Long id) {
        phimService.delete(id);
        return "redirect:/admin";
    }

    // ===== Quản lý Thể Loại =====
    @GetMapping("/the-loai/new")
    public String addTheLoaiForm(Model model) {
        model.addAttribute("theLoai", new TheLoai());
        return "admin/theloai_form";
    }

    @PostMapping("/the-loai/save")
    public String saveTheLoai(@ModelAttribute TheLoai theLoai) {
        theLoaiService.save(theLoai);
        return "redirect:/admin";
    }

    @GetMapping("/the-loai/delete/{id}")
    public String deleteTheLoai(@PathVariable Long id) {
        theLoaiService.delete(id);
        return "redirect:/admin";
    }

    // ===== Quản lý Người Dùng =====
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        nguoiDungService.delete(id);
        return "redirect:/admin";
    }

    // ===== Quản lý Bình Luận =====
    @GetMapping("/binh-luan/delete/{id}")
    public String deleteBinhLuan(@PathVariable Long id) {
        binhLuanService.delete(id);
        return "redirect:/admin";
    }
}
