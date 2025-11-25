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

    // ===== Dashboard =====
    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("adminName", "Admin");
        model.addAttribute("phimCount", phimService.findAll().size());
        model.addAttribute("theLoaiCount", theLoaiService.findAll().size());
        model.addAttribute("nguoiDungCount", nguoiDungService.findAll().size());
        model.addAttribute("binhLuanCount", binhLuanService.findAll().size());
        return "admin/index";
    }

    // ========================================================================
// ============================== PHIM ===================================
// ========================================================================

    @GetMapping("/phim")
    public String listPhim(Model model) {
        model.addAttribute("phimList", phimService.findAll());
        // trả về template admin/phim/phim_list.html
        return "admin/phim/phim_list";
    }

    @GetMapping("/phim/new")
    public String addPhimForm(Model model) {
        model.addAttribute("phim", new Phim());
        model.addAttribute("theLoais", theLoaiService.findAll());
        return "admin/phim/phim_form";
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
        return "redirect:/admin/phim"; // giữ redirect về list phim
    }

    @GetMapping("/phim/edit/{id}")
    public String editPhimForm(@PathVariable Long id, Model model) {
        Phim phim = phimService.findById(id)
                .orElseThrow(() -> new RuntimeException("Phim không tồn tại"));

        model.addAttribute("phim", phim);
        model.addAttribute("theLoais", theLoaiService.findAll());
        return "admin/phim/phim_form";
    }

    @GetMapping("/phim/delete/{id}")
    public String deletePhim(@PathVariable Long id) {
        phimService.delete(id);
        return "redirect:/admin/phim"; // giữ redirect về list phim
    }


    // ========================================================================
    // ============================== THỂ LOẠI ================================
    // ========================================================================

    @GetMapping("/theloai")
    public String listTheLoai(Model model) {
        model.addAttribute("theLoaiList", theLoaiService.findAll());
        return "admin/theloai_list";
    }

    @GetMapping("/theloai/new")
    public String addTheLoaiForm(Model model) {
        model.addAttribute("theLoai", new TheLoai());
        return "admin/theloai_form";
    }

    @PostMapping("/theloai/save")
    public String saveTheLoai(@ModelAttribute TheLoai theLoai) {
        theLoaiService.save(theLoai);
        return "redirect:/admin/theloai";
    }

    @GetMapping("/theloai/delete/{id}")
    public String deleteTheLoai(@PathVariable Long id) {
        theLoaiService.delete(id);
        return "redirect:/admin/theloai";
    }

    // ========================================================================
    // ============================== NGƯỜI DÙNG ==============================
    // ========================================================================

    @GetMapping("/nguoidung")
    public String listNguoiDung(Model model) {
        model.addAttribute("userList", nguoiDungService.findAll());
        return "admin/nguoidung_list";
    }

    @GetMapping("/nguoidung/delete/{id}")
    public String deleteNguoiDung(@PathVariable Long id) {
        nguoiDungService.delete(id);
        return "redirect:/admin/nguoidung";
    }

    // ========================================================================
    // ============================== BÌNH LUẬN ===============================
    // ========================================================================

    @GetMapping("/binhluan")
    public String listBinhLuan(Model model) {
        model.addAttribute("binhLuanList", binhLuanService.findAll());
        return "admin/binhluan_list";
    }

    @GetMapping("/binhluan/delete/{id}")
    public String deleteBinhLuan(@PathVariable Long id) {
        binhLuanService.delete(id);
        return "redirect:/admin/binhluan";
    }
}
