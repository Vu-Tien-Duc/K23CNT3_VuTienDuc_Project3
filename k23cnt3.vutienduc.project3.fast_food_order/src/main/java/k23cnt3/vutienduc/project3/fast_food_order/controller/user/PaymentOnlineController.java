package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import k23cnt3.vutienduc.project3.fast_food_order.config.PaymentQRUtil;
import k23cnt3.vutienduc.project3.fast_food_order.entity.DonHang;
import k23cnt3.vutienduc.project3.fast_food_order.entity.TrangThaiDonHang;
import k23cnt3.vutienduc.project3.fast_food_order.repository.DonHangRepository;
import k23cnt3.vutienduc.project3.fast_food_order.repository.NguoiDungRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/payment-online")
@RequiredArgsConstructor
public class PaymentOnlineController {

    private final DonHangRepository donHangRepository;
    private final NguoiDungRepository nguoiDungRepository;

    // ================== Hiển thị trang thanh toán Online với QR ==================
    @GetMapping("/{donHangId}")
    public String paymentOnline(@PathVariable Long donHangId,
                                Model model,
                                Principal principal) throws Exception {

        // Kiểm tra và add user login
        addLoggedUser(model, principal);

        DonHang donHang = donHangRepository.findById(donHangId)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));

        // Sinh QR Base64 với số tiền động từ đơn hàng
        String qrBase64 = PaymentQRUtil.generateQR(
                "970422",                        // BIN ngân hàng (MB Bank)
                "0345578911111",                 // STK
                donHang.getThanhToan().getSoTien(),  // Số tiền
                "Thanh toán đơn #" + donHang.getId() // Nội dung thanh toán
        );

        model.addAttribute("donHang", donHang);
        model.addAttribute("qrCodeUrl", "data:image/png;base64," + qrBase64);

        return "user/payment/online";
    }

    // ================== Xử lý khi user xác nhận đã thanh toán ==================
    @PostMapping("/{donHangId}/success")
    public String paymentSuccess(@PathVariable Long donHangId,
                                 Principal principal) {

        // Kiểm tra user login
        if (principal == null) {
            return "redirect:/login";
        }

        DonHang donHang = donHangRepository.findById(donHangId)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));

        // Cập nhật trạng thái thanh toán
        donHang.getThanhToan().setTrangThai("DA_THANH_TOAN");

        // DonHang vẫn ở trạng thái CHO_XU_LY, chưa giao
        donHang.setTrangThai(TrangThaiDonHang.CHO_XU_LY);

        donHangRepository.save(donHang);

        return "redirect:/checkout/success";
    }

    // ================== Phương thức add user login vào model ==================
    private void addLoggedUser(Model model, Principal principal) {
        if (principal != null) {
            nguoiDungRepository.findByEmail(principal.getName())
                    .ifPresent(user -> model.addAttribute("nguoiDung", user));
        }
    }
}
