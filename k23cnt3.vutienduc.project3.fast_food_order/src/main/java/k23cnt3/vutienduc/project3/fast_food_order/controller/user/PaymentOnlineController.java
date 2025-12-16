package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import k23cnt3.vutienduc.project3.fast_food_order.config.PaymentQRUtil;
import k23cnt3.vutienduc.project3.fast_food_order.entity.DonHang;
import k23cnt3.vutienduc.project3.fast_food_order.entity.TrangThaiDonHang;
import k23cnt3.vutienduc.project3.fast_food_order.repository.DonHangRepository;
import k23cnt3.vutienduc.project3.fast_food_order.repository.NguoiDungRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/payment-online")
public class PaymentOnlineController extends BaseController {

    private final DonHangRepository donHangRepository;

    public PaymentOnlineController(
            NguoiDungRepository nguoiDungRepository,
            DonHangRepository donHangRepository
    ) {
        super(nguoiDungRepository);
        this.donHangRepository = donHangRepository;
    }

    @GetMapping("/{id}")
    public String paymentOnline(
            @PathVariable Long id,
            Model model,
            Principal principal
    ) throws Exception {

        addLoggedUser(model, principal);

        DonHang donHang = donHangRepository.findById(id)
                .orElseThrow();

        String qrBase64 = PaymentQRUtil.generateQR(
                "Vu Tien Duc",
                "0345578911111",
                donHang.getThanhToan().getSoTien(),
                "Thanh toan don #" + donHang.getId()
        );

        model.addAttribute("donHang", donHang);
        model.addAttribute("qrCodeUrl", "data:image/png;base64," + qrBase64);

        return "user/payment/online";
    }

    @PostMapping("/{id}/success")
    public String paymentSuccess(@PathVariable Long id) {

        DonHang donHang = donHangRepository.findById(id)
                .orElseThrow();

        donHang.getThanhToan().setTrangThai("DA_THANH_TOAN");
        donHang.setTrangThai(TrangThaiDonHang.CHO_XU_LY);

        donHangRepository.save(donHang);

        return "redirect:/checkout/success";
    }
}
