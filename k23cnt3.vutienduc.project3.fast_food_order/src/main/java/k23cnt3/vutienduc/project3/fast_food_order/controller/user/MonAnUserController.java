package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import jakarta.servlet.http.HttpSession;
import k23cnt3.vutienduc.project3.fast_food_order.entity.BinhLuan;
import k23cnt3.vutienduc.project3.fast_food_order.entity.MonAn;
import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import k23cnt3.vutienduc.project3.fast_food_order.repository.NguoiDungRepository;
import k23cnt3.vutienduc.project3.fast_food_order.service.BinhLuanService;
import k23cnt3.vutienduc.project3.fast_food_order.service.MonAnService;
import k23cnt3.vutienduc.project3.fast_food_order.service.TheLoaiService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/mon-an")
public class MonAnUserController extends BaseController {

    private final MonAnService monAnService;
    private final TheLoaiService theLoaiService;
    private final BinhLuanService binhLuanService;

    // ✅ Constructor rõ ràng + super()
    public MonAnUserController(
            NguoiDungRepository nguoiDungRepository,
            MonAnService monAnService,
            TheLoaiService theLoaiService,
            BinhLuanService binhLuanService
    ) {
        super(nguoiDungRepository);
        this.monAnService = monAnService;
        this.theLoaiService = theLoaiService;
        this.binhLuanService = binhLuanService;
    }

    // -----------------------------------------------------
    // DANH SÁCH MÓN ĂN (SEARCH + FILTER + PAGINATION)
    // -----------------------------------------------------
    @GetMapping
    public String list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long theLoaiId,
            Model model,
            Principal principal,
            HttpSession session
    ) {

        addLoggedUser(model, principal);
        addCartCount(model, session);

        Page<MonAn> pageData = monAnService.getAll(page, size, search, theLoaiId);

        model.addAttribute("pageData", pageData);
        model.addAttribute("search", search);
        model.addAttribute("theLoaiId", theLoaiId);
        model.addAttribute("dsTheLoai", theLoaiService.getAll());

        // ⭐ Gợi ý random món ăn
        List<MonAn> all = monAnService.findAll();
        Collections.shuffle(all);
        model.addAttribute("monAnList", all.stream().limit(12).toList());

        return "user/mon-an/index";
    }

    // -----------------------------------------------------
    // CHI TIẾT MÓN ĂN
    // -----------------------------------------------------
    @GetMapping("/{id}")
    public String detail(
            @PathVariable Long id,
            Model model,
            Principal principal,
            HttpSession session
    ) {

        addLoggedUser(model, principal);
        addCartCount(model, session);

        MonAn monAn = monAnService.getById(id);
        if (monAn == null) {
            return "redirect:/mon-an?error=notfound";
        }

        model.addAttribute("monAn", monAn);

        // Gợi ý món cùng thể loại
        if (monAn.getTheLoai() != null) {
            List<MonAn> goiY = monAnService
                    .getByTheLoai(monAn.getTheLoai().getId())
                    .stream()
                    .filter(m -> !m.getId().equals(id))
                    .limit(6)
                    .collect(Collectors.toList());

            model.addAttribute("monAnList", goiY);
        } else {
            model.addAttribute("monAnList", List.of());
        }

        // Bình luận
        List<BinhLuan> dsBinhLuan = binhLuanService.findByMonAn(id);
        model.addAttribute("dsBinhLuan", dsBinhLuan);

        // ⭐ AVG + COUNT rating
        Double avgRating = binhLuanService.getAverageRatingByMonAn(id);
        long tongDanhGia = binhLuanService.countByMonAn(id);

        model.addAttribute("avgRating", avgRating != null ? avgRating : 0);
        model.addAttribute("tongDanhGia", tongDanhGia);

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
            Principal principal
    ) {

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
    // AUTOCOMPLETE SEARCH
    // -----------------------------------------------------
    @GetMapping("/search-suggestions")
    @ResponseBody
    public List<?> searchSuggestions(@RequestParam String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) return List.of();

        return monAnService.findAll()
                .stream()
                .filter(m -> m.getTen().toLowerCase().contains(keyword.toLowerCase()))
                .limit(8)
                .map(m -> Map.of(
                        "id", m.getId(),
                        "ten", m.getTen(),
                        "gia", m.getGia(),
                        "hinhAnh", m.getHinhAnh()
                ))
                .collect(Collectors.toList());
    }
}
