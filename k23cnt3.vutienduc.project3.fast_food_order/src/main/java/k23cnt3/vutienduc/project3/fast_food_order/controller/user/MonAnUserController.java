package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import k23cnt3.vutienduc.project3.fast_food_order.entity.MonAn;
import k23cnt3.vutienduc.project3.fast_food_order.entity.TheLoai;
import k23cnt3.vutienduc.project3.fast_food_order.service.MonAnService;
import k23cnt3.vutienduc.project3.fast_food_order.service.TheLoaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MonAnUserController {

    private final MonAnService monAnService;
    private final TheLoaiService theLoaiService;

    /**
     * Trang danh sách món ăn: /mon-an hoặc /mon-an/index
     * - search (optional)
     * - theLoaiId (optional)
     * - page (optional)
     */
    @GetMapping({"/mon-an", "/mon-an/index"})
    public String index(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long theLoai,
            @RequestParam(required = false) String tenTheLoai
    ) {

        Page<MonAn> monAnPage = monAnService.getAll(page, size, search, theLoai, tenTheLoai);

        model.addAttribute("monAnPage", monAnPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", monAnPage.getTotalPages());

        model.addAttribute("search", search);
        model.addAttribute("theLoaiId", theLoai);
        model.addAttribute("tenTheLoai", tenTheLoai);

        model.addAttribute("theLoaiList", theLoaiService.getAll());

        return "user/mon-an/index";
    }


    /**
     * Chi tiết món ăn: /mon-an/{id}
     */
    @GetMapping("/mon-an/{id}")
    public String detail(@PathVariable Long id, Model model) {
        MonAn monAn = monAnService.getById(id);
        model.addAttribute("monAn", monAn);

        // Lấy thêm danh sách các món khác cùng thể loại (gợi ý)
        if (monAn.getTheLoai() != null) {
            List<MonAn> related = monAnService.getByTheLoai(monAn.getTheLoai().getId());
            related.removeIf(m -> m.getId().equals(monAn.getId())); // bỏ món hiện tại
            model.addAttribute("related", related);
        }

        return "user/mon-an/detail";
    }

}
