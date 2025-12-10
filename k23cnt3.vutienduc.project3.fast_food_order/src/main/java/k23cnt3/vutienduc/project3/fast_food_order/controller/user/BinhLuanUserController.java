package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import jakarta.servlet.http.HttpSession;
import k23cnt3.vutienduc.project3.fast_food_order.entity.BinhLuan;
import k23cnt3.vutienduc.project3.fast_food_order.entity.MonAn;
import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import k23cnt3.vutienduc.project3.fast_food_order.service.BinhLuanService;
import k23cnt3.vutienduc.project3.fast_food_order.service.MonAnService;
import k23cnt3.vutienduc.project3.fast_food_order.service.NguoiDungService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/binh-luan")
@RequiredArgsConstructor
public class BinhLuanUserController {

    private final BinhLuanService binhLuanService;
    private final NguoiDungService nguoiDungService;
    private final MonAnService monAnService;

    @PostMapping("/add")
    public String add(@RequestParam Long monAnId,
                      @RequestParam Integer rating,
                      @RequestParam String noiDung,
                      HttpSession session) {

        // Lấy user từ session hoặc từ service
        NguoiDung user = (NguoiDung) session.getAttribute("nguoiDung");

        if (user == null) {
            user = nguoiDungService.getProfile();
        }

        MonAn monAn = monAnService.getById(monAnId);

        BinhLuan bl = new BinhLuan();
        bl.setMonAn(monAn);
        bl.setNguoiDung(user);
        bl.setNoiDung(noiDung);
        bl.setDanhGia(rating);

        binhLuanService.save(bl);

        return "redirect:/mon-an/" + monAnId;
    }
}
