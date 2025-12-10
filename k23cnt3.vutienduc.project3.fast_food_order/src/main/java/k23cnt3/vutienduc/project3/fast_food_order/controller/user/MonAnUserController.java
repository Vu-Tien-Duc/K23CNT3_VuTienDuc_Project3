package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import k23cnt3.vutienduc.project3.fast_food_order.entity.MonAn;
import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import k23cnt3.vutienduc.project3.fast_food_order.repository.NguoiDungRepository;
import k23cnt3.vutienduc.project3.fast_food_order.service.BinhLuanService;
import k23cnt3.vutienduc.project3.fast_food_order.service.MonAnService;
import k23cnt3.vutienduc.project3.fast_food_order.service.TheLoaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/mon-an")
@RequiredArgsConstructor
public class MonAnUserController {

    private final MonAnService monAnService;
    private final TheLoaiService theLoaiService;
    private final BinhLuanService binhLuanService;
    private final NguoiDungRepository nguoiDungRepository;

    // Dùng chung để gửi thông tin user login lên layout
    private void addLoggedUser(Model model, Principal principal) {
        if (principal != null) {
            NguoiDung nd = nguoiDungRepository
                    .findByEmail(principal.getName())
                    .orElse(null);
            model.addAttribute("nguoiDung", nd);
        } else {
            model.addAttribute("nguoiDung", null);
        }
    }

    // ===============================
    // 🔥 DANH SÁCH MÓN ĂN + LỌC + SEARCH + PHÂN TRANG
    // ===============================
    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "8") int size,
                       @RequestParam(required = false) String search,
                       @RequestParam(required = false) Long theLoaiId,
                       Model model,
                       Principal principal) {

        addLoggedUser(model, principal);

        // Lấy dữ liệu phân trang
        model.addAttribute("pageData",
                monAnService.getAll(page, size, search, theLoaiId));

        model.addAttribute("search", search);
        model.addAttribute("theLoaiId", theLoaiId);
        model.addAttribute("dsTheLoai", theLoaiService.getAll());

        return "user/mon-an/index";
    }

    // ===============================
    // 🔥 TRANG CHI TIẾT MÓN ĂN
    // ===============================
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id,
                         Model model,
                         Principal principal) {

        addLoggedUser(model, principal);

        MonAn monAn = monAnService.getById(id);

        model.addAttribute("monAn", monAn);
        model.addAttribute("theLoai", monAn.getTheLoai());
        model.addAttribute("goiYMons",
                monAnService.getByTheLoai(monAn.getTheLoai().getId()));

        // Bình luận + Đánh giá
        model.addAttribute("binhLuans", binhLuanService.filterBinhLuan(id, null, null));
        model.addAttribute("avgRating", binhLuanService.getAverageRatingByMonAn(id));
        model.addAttribute("tongDanhGia", binhLuanService.countByMonAn(id));

        return "user/mon-an/detail";
    }
}
