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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/mon-an")
@RequiredArgsConstructor
public class MonAnAdminController {

    private final MonAnService monAnService;
    private final TheLoaiService theLoaiService;

    // List món ăn có phân trang
    @GetMapping
    public String list(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long theLoaiId
    ) {
        Page<MonAn> monAnPage = monAnService.getAll(page, size, search, theLoaiId);
        List<TheLoai> theLoaiList = theLoaiService.getAll();

        model.addAttribute("monAnPage", monAnPage);
        model.addAttribute("theLoaiList", theLoaiList);
        model.addAttribute("search", search);
        model.addAttribute("theLoaiId", theLoaiId);

        return "admin/mon-an/list";
    }

    // Show form tạo món ăn mới
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        List<TheLoai> theLoaiList = theLoaiService.getAll();
        model.addAttribute("monAn", new MonAn());
        model.addAttribute("theLoaiList", theLoaiList);
        return "admin/mon-an/form";
    }

    // Xử lý tạo món ăn mới
    @PostMapping("/create")
    public String createMonAn(@ModelAttribute MonAn monAn,
                              @RequestParam(required = false) String hinhAnhStr) {

        if (hinhAnhStr != null && !hinhAnhStr.isBlank()) {
            monAn.setHinhAnh(Arrays.asList(hinhAnhStr.split("\\s*,\\s*")));
        } else {
            monAn.setHinhAnh(new ArrayList<>());
        }

        monAnService.create(monAn);
        return "redirect:/admin/mon-an";
    }

    // Show form chỉnh sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        MonAn monAn = monAnService.getById(id);
        if (monAn.getHinhAnh() == null) {
            monAn.setHinhAnh(new ArrayList<>());
        }
        List<TheLoai> theLoaiList = theLoaiService.getAll();
        model.addAttribute("monAn", monAn);
        model.addAttribute("theLoaiList", theLoaiList);
        return "admin/mon-an/form";
    }

    // Xử lý cập nhật
    @PostMapping("/edit/{id}")
    public String updateMonAn(@PathVariable Long id,
                              @ModelAttribute MonAn monAn,
                              @RequestParam Long theLoaiId,
                              @RequestParam(required = false) String hinhAnhStr) {

        monAn.setTheLoai(theLoaiService.getById(theLoaiId));

        if (hinhAnhStr != null && !hinhAnhStr.isBlank()) {
            monAn.setHinhAnh(Arrays.asList(hinhAnhStr.split("\\s*,\\s*")));
        } else {
            monAn.setHinhAnh(new ArrayList<>());
        }

        monAnService.update(id, monAn);
        return "redirect:/admin/mon-an";
    }


    // Xóa món ăn
    @GetMapping("/delete/{id}")
    public String deleteMonAn(@PathVariable Long id, Model model) {
        try {
            monAnService.delete(id);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "admin/mon-an/list";
        }
        return "redirect:/admin/mon-an";
    }
}
