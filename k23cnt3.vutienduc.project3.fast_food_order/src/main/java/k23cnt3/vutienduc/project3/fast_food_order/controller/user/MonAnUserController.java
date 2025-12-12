package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import k23cnt3.vutienduc.project3.fast_food_order.entity.BinhLuan;
import k23cnt3.vutienduc.project3.fast_food_order.entity.MonAn;
import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import k23cnt3.vutienduc.project3.fast_food_order.repository.NguoiDungRepository;
import k23cnt3.vutienduc.project3.fast_food_order.service.BinhLuanService;
import k23cnt3.vutienduc.project3.fast_food_order.service.MonAnService;
import k23cnt3.vutienduc.project3.fast_food_order.service.TheLoaiService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/mon-an")
@RequiredArgsConstructor
public class MonAnUserController {

    private final MonAnService monAnService;
    private final TheLoaiService theLoaiService;
    private final BinhLuanService binhLuanService;
    private final NguoiDungRepository nguoiDungRepository;

    // -----------------------------------------------------
    // ĐƯA NGƯỜI DÙNG LOGIN VÀO _LayoutUser
    // -----------------------------------------------------
    private void addLoggedUser(Model model, Principal principal) {
        if (principal != null) {
            NguoiDung nd = nguoiDungRepository.findByEmail(principal.getName()).orElse(null);
            model.addAttribute("nguoiDung", nd);
        } else {
            model.addAttribute("nguoiDung", null);
        }
    }

    // -----------------------------------------------------
    // DANH SÁCH MÓN ĂN (SEARCH + FILTER + PHÂN TRANG)
    // -----------------------------------------------------
    @GetMapping
    public String list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long theLoaiId,
            Model model,
            Principal principal) {

        addLoggedUser(model, principal);

        Page<MonAn> pageData = monAnService.getAll(page, size, search, theLoaiId);

        model.addAttribute("pageData", pageData);
        model.addAttribute("search", search);
        model.addAttribute("theLoaiId", theLoaiId);

        model.addAttribute("dsTheLoai", theLoaiService.getAll());

        // ⭐⭐⭐ GỢI Ý RANDOM — KHÔNG SỬA SERVICE ⭐⭐⭐
        List<MonAn> all = monAnService.findAll();
        Collections.shuffle(all);
        model.addAttribute("monAnList", all.stream().limit(8).toList());

        return "user/mon-an/index";
    }


    @GetMapping("/{id}")
    public String detail(
            @PathVariable Long id,
            Model model,
            Principal principal) {

        addLoggedUser(model, principal);

        MonAn monAn = monAnService.getById(id);
        if (monAn == null) {
            return "redirect:/mon-an?error=notfound";
        }

        model.addAttribute("monAn", monAn);

        // Gợi ý món cùng thể loại
        if (monAn.getTheLoai() != null) {
            List<MonAn> goiY = monAnService.getByTheLoai(monAn.getTheLoai().getId())
                    .stream()
                    .filter(m -> !m.getId().equals(id))
                    .limit(6)
                    .collect(Collectors.toList());

            model.addAttribute("monAnList", goiY);
        } else {
            model.addAttribute("monAnList", new ArrayList<>());
        }

        // Lấy danh sách bình luận
        List<BinhLuan> dsBinhLuan = binhLuanService.findByMonAn(id);
        model.addAttribute("dsBinhLuan", dsBinhLuan);

        // ⭐⭐⭐ THÊM PHẦN QUAN TRỌNG NHẤT — AVG + COUNT ⭐⭐⭐
        Double avgRating = binhLuanService.getAverageRatingByMonAn(id);
        long tongDanhGia = binhLuanService.countByMonAn(id);

        model.addAttribute("avgRating", avgRating != null ? avgRating : 0);
        model.addAttribute("tongDanhGia", tongDanhGia);

        // Object form
        model.addAttribute("newComment", new BinhLuan());

        return "user/mon-an/detail";
    }


    // -----------------------------------------------------
    // THÊM BÌNH LUẬN
    // -----------------------------------------------------
    @PostMapping("/binh-luan/add")
    public String addBinhLuan(
            @RequestParam Long monAnId,
            @RequestParam String noiDung,
            @RequestParam int danhGia,
            Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        NguoiDung nguoiDung = nguoiDungRepository
                .findByEmail(principal.getName())
                .orElse(null);

        MonAn monAn = monAnService.getById(monAnId);
        if (monAn == null) {
            return "redirect:/mon-an?error=notfound";
        }

        BinhLuan bl = new BinhLuan();
        bl.setMonAn(monAn);
        bl.setNguoiDung(nguoiDung);
        bl.setNoiDung(noiDung);
        bl.setDanhGia(danhGia);

        binhLuanService.save(bl);

        return "redirect:/mon-an/" + monAnId + "#binh-luan";
    }

    // -----------------------------------------------------
    // AUTOCOMPLETE TÊN MÓN ĂN
    // -----------------------------------------------------
    @GetMapping("/search-suggestions")
    @ResponseBody
    public List<?> searchSuggestions(@RequestParam String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return List.of();

        return monAnService.findAll()
                .stream()
                .filter(m -> m.getTen().toLowerCase().contains(keyword.toLowerCase()))
                .limit(8)
                .map(m -> new Object() {
                    public final Long id = m.getId();
                    public final String ten = m.getTen();
                    public final double gia = m.getGia();
                    public final List<String> hinhAnh = m.getHinhAnh();
                })
                .collect(Collectors.toList());
    }
}
