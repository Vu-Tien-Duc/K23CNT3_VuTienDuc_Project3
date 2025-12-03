package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import k23cnt3.vutienduc.project3.fast_food_order.entity.BinhLuan;
import k23cnt3.vutienduc.project3.fast_food_order.service.BinhLuanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/binh-luan")
public class BinhLuanController {

    private final BinhLuanService binhLuanService;

    @GetMapping("/mon-an/{monAnId}")
    public String getByMonAn(@PathVariable Long monAnId, Model model) {
        List<BinhLuan> ds = binhLuanService.getByMonAn(monAnId);
        model.addAttribute("binhLuans", ds);
        return "user/binh-luan/list";
    }

    @PostMapping("/create")
    public String create(BinhLuan binhLuan) {
        binhLuanService.create(binhLuan);
        return "redirect:/binh-luan/mon-an/" + binhLuan.getMonAn().getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        binhLuanService.delete(id);
        return "redirect:/don-hang/my-orders"; // redirect về danh sách đơn hàng hoặc trang trước
    }
}
