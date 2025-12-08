package k23cnt3.vutienduc.project3.fast_food_order.controller.admin;

import k23cnt3.vutienduc.project3.fast_food_order.entity.MonAn;
import k23cnt3.vutienduc.project3.fast_food_order.entity.TheLoai;
import k23cnt3.vutienduc.project3.fast_food_order.service.MonAnService;
import k23cnt3.vutienduc.project3.fast_food_order.service.TheLoaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/mon-an")
@RequiredArgsConstructor
public class MonAnAdminController {

    private final MonAnService monAnService;
    private final TheLoaiService theLoaiService;

    // ========================= LIST ==============================
    @GetMapping
    public String list(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long theLoaiId) {

        Page<MonAn> monAnPage = monAnService.getAll(page, size, search, theLoaiId);
        List<TheLoai> theLoaiList = theLoaiService.getAll();

        model.addAttribute("monAnPage", monAnPage);
        model.addAttribute("theLoaiList", theLoaiList);
        model.addAttribute("search", search);
        model.addAttribute("theLoaiId", theLoaiId);

        return "admin/mon-an/list";
    }

    // ========================= CREATE ===========================
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("monAn", new MonAn());
        model.addAttribute("theLoaiList", theLoaiService.getAll());
        model.addAttribute("hinhAnhStr", "");
        return "admin/mon-an/form";
    }

    @PostMapping("/create")
    public String createMonAn(
            @ModelAttribute MonAn monAn,
            @RequestParam(required = false) String hinhAnhStr,
            @RequestParam(required = false) Long theLoaiId,
            @RequestParam(required = false) Double giaCu,
            Model model) {

        if (theLoaiId == null) {
            model.addAttribute("error", "Vui lòng chọn thể loại");
            model.addAttribute("monAn", monAn);
            model.addAttribute("theLoaiList", theLoaiService.getAll());
            model.addAttribute("hinhAnhStr", hinhAnhStr == null ? "" : hinhAnhStr);
            return "admin/mon-an/form";
        }

        monAn.setTheLoai(theLoaiService.getById(theLoaiId));
        monAn.setGiaCu(giaCu);

        if (hinhAnhStr != null && !hinhAnhStr.isBlank()) {
            monAn.setHinhAnh(Arrays.asList(hinhAnhStr.split("\\s*,\\s*")));
        } else {
            monAn.setHinhAnh(new ArrayList<>());
        }

        monAnService.create(monAn);
        return "redirect:/admin/mon-an";
    }

    // ========================= EDIT ==============================
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {

        MonAn monAn = monAnService.getById(id);

        String hinhAnhStr = (monAn.getHinhAnh() != null && !monAn.getHinhAnh().isEmpty())
                ? String.join(",", monAn.getHinhAnh())
                : "";

        model.addAttribute("monAn", monAn);
        model.addAttribute("theLoaiList", theLoaiService.getAll());
        model.addAttribute("hinhAnhStr", hinhAnhStr);

        return "admin/mon-an/form";
    }

    @PostMapping("/edit/{id}")
    public String updateMonAn(
            @PathVariable Long id,
            @ModelAttribute MonAn form,
            @RequestParam(required = false) String hinhAnhStr,
            @RequestParam(required = false) Long theLoaiId,
            @RequestParam(required = false) Double giaCu,
            Model model) {

        if (theLoaiId == null) {
            model.addAttribute("error", "Vui lòng chọn thể loại");
            model.addAttribute("monAn", form);
            model.addAttribute("theLoaiList", theLoaiService.getAll());
            model.addAttribute("hinhAnhStr", hinhAnhStr == null ? "" : hinhAnhStr);
            return "admin/mon-an/form";
        }

        // Lấy món ăn cũ
        MonAn existing = monAnService.getById(id);

        // Cập nhật dữ liệu
        existing.setTen(form.getTen());
        existing.setMoTa(form.getMoTa());
        existing.setGia(form.getGia());
        existing.setGiaCu(giaCu); // <<< BẠN BỊ THIẾU DÒNG NÀY TRƯỚC ĐÓ
        existing.setTheLoai(theLoaiService.getById(theLoaiId));

        if (hinhAnhStr != null && !hinhAnhStr.isBlank()) {
            existing.setHinhAnh(Arrays.asList(hinhAnhStr.split("\\s*,\\s*")));
        } else {
            existing.setHinhAnh(new ArrayList<>());
        }

        monAnService.update(id, existing);
        return "redirect:/admin/mon-an";
    }

    // ========================= DELETE ===========================
    @GetMapping("/delete/{id}")
    public String deleteMonAn(@PathVariable Long id, Model model) {
        try {
            monAnService.delete(id);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("monAnPage", monAnService.getAll(0, 5, null, null));
            model.addAttribute("theLoaiList", theLoaiService.getAll());
            return "admin/mon-an/list";
        }
        return "redirect:/admin/mon-an";
    }
}
