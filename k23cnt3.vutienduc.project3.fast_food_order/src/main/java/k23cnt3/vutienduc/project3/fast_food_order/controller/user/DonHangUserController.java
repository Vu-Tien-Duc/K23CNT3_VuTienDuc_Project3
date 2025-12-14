package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import jakarta.servlet.http.HttpSession;
import k23cnt3.vutienduc.project3.fast_food_order.entity.DonHang;
import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import k23cnt3.vutienduc.project3.fast_food_order.entity.TrangThaiDonHang;
import k23cnt3.vutienduc.project3.fast_food_order.repository.DonHangRepository;
import k23cnt3.vutienduc.project3.fast_food_order.repository.NguoiDungRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class DonHangUserController {

    private final DonHangRepository donHangRepository;
    private final NguoiDungRepository nguoiDungRepository;

    /* ================== DANH SÁCH ĐƠN HÀNG ================== */
    @GetMapping
    public String listDonHang(Model model,
                              Principal principal,
                              HttpSession session) {

        if (principal == null) {
            return "redirect:/login";
        }

        // ✅ user đăng nhập
        addLoggedUser(model, principal);

        // ✅ số lượng giỏ hàng
        addCartCount(model, session);

        NguoiDung nguoiDung = nguoiDungRepository
                .findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        List<DonHang> donHangs =
                donHangRepository.findByNguoiDungOrderByNgayDatDesc(nguoiDung);

        model.addAttribute("donHangs", donHangs);

        return "user/don-hang/index";
    }

    /* ================== CHI TIẾT ĐƠN HÀNG ================== */
    @GetMapping("/{id}")
    public String donHangDetail(@PathVariable Long id,
                                Model model,
                                Principal principal,
                                HttpSession session) {

        if (principal == null) {
            return "redirect:/login";
        }

        // ✅ user đăng nhập
        addLoggedUser(model, principal);

        // ✅ số lượng giỏ hàng
        addCartCount(model, session);

        NguoiDung nguoiDung = nguoiDungRepository
                .findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        DonHang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));

        // 🔒 Không cho xem đơn của người khác
        if (!donHang.getNguoiDung().getId().equals(nguoiDung.getId())) {
            return "redirect:/orders";
        }

        model.addAttribute("donHang", donHang);

        return "user/don-hang/detail";
    }

    /* ================== HỦY ĐƠN HÀNG ================== */
    @PostMapping("/{id}/cancel")
    public String cancelOrder(@PathVariable Long id,
                              Principal principal,
                              HttpSession session) {

        if (principal == null) {
            return "redirect:/login";
        }

        NguoiDung nguoiDung = nguoiDungRepository
                .findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        DonHang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));

        // 🔒 Chỉ chủ đơn
        if (!donHang.getNguoiDung().getId().equals(nguoiDung.getId())) {
            return "redirect:/orders";
        }

        // ❌ Chỉ hủy khi CHO_XU_LY
        if (donHang.getTrangThai() != TrangThaiDonHang.CHO_XU_LY) {
            return "redirect:/orders";
        }

        // ✅ Hủy đơn
        donHang.setTrangThai(TrangThaiDonHang.DA_HUY);

        // ✅ Nếu đã thanh toán online → hoàn tiền (mô phỏng)
        if (donHang.getThanhToan() != null &&
                "DA_THANH_TOAN".equals(donHang.getThanhToan().getTrangThai())) {

            donHang.getThanhToan().setTrangThai("HOAN_TIEN");
        }

        donHangRepository.save(donHang);

        return "redirect:/orders";
    }

    /* ================== HÀM DÙNG CHUNG ================== */

    // nếu 2 hàm này nằm ở BaseController thì bỏ phần dưới đi
    private void addLoggedUser(Model model, Principal principal) {
        if (principal != null) {
            nguoiDungRepository.findByEmail(principal.getName())
                    .ifPresent(user -> model.addAttribute("nguoiDung", user));
        }
    }

    private void addCartCount(Model model, HttpSession session) {
        int cartCount = 0;
        Object cartObj = session.getAttribute("CART");
        if (cartObj instanceof java.util.Map<?, ?> cart) {
            for (Object value : cart.values()) {
                if (value instanceof Integer) {
                    cartCount += (Integer) value;
                }
            }
        }
        model.addAttribute("cartCount", cartCount);
    }
}
