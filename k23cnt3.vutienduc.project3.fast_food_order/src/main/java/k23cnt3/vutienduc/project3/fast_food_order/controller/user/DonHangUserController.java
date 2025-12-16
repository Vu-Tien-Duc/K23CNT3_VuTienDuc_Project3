package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import jakarta.servlet.http.HttpSession;
import k23cnt3.vutienduc.project3.fast_food_order.entity.DonHang;
import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import k23cnt3.vutienduc.project3.fast_food_order.entity.TrangThaiDonHang;
import k23cnt3.vutienduc.project3.fast_food_order.repository.DonHangRepository;
import k23cnt3.vutienduc.project3.fast_food_order.repository.NguoiDungRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class DonHangUserController extends BaseController {

    private final DonHangRepository donHangRepository;

    // ===== CONSTRUCTOR CHUẨN BASE =====
    public DonHangUserController(
            DonHangRepository donHangRepository,
            NguoiDungRepository nguoiDungRepository
    ) {
        super(nguoiDungRepository);
        this.donHangRepository = donHangRepository;
    }

    /* ================== DANH SÁCH ĐƠN HÀNG ================== */
    @GetMapping
    public String listDonHang(
            Model model,
            Principal principal,
            HttpSession session
    ) {

        // 🔐 chưa login
        if (principal == null) {
            return "redirect:/login";
        }

        // ✅ dùng BASE
        addLoggedUser(model, principal);   // -> loggedUser
        addCartCount(model, session);

        // user để query DB
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
    public String donHangDetail(
            @PathVariable Long id,
            Model model,
            Principal principal,
            HttpSession session
    ) {

        if (principal == null) {
            return "redirect:/login";
        }

        addLoggedUser(model, principal);
        addCartCount(model, session);

        NguoiDung nguoiDung = nguoiDungRepository
                .findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        DonHang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));

        // 🔒 không cho xem đơn của người khác
        if (!donHang.getNguoiDung().getId().equals(nguoiDung.getId())) {
            return "redirect:/orders";
        }

        model.addAttribute("donHang", donHang);

        return "user/don-hang/detail";
    }

    /* ================== HỦY ĐƠN HÀNG ================== */
    @PostMapping("/{id}/cancel")
    public String cancelOrder(
            @PathVariable Long id,
            Principal principal,
            HttpSession session
    ) {

        if (principal == null) {
            return "redirect:/login";
        }

        NguoiDung nguoiDung = nguoiDungRepository
                .findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        DonHang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));

        // 🔒 chỉ chủ đơn
        if (!donHang.getNguoiDung().getId().equals(nguoiDung.getId())) {
            return "redirect:/orders";
        }

        // ❌ chỉ hủy khi CHO_XU_LY
        if (donHang.getTrangThai() != TrangThaiDonHang.CHO_XU_LY) {
            return "redirect:/orders";
        }

        // ✅ hủy đơn
        donHang.setTrangThai(TrangThaiDonHang.DA_HUY);

        // ✅ nếu đã thanh toán online → hoàn tiền (mock)
        if (donHang.getThanhToan() != null &&
                "DA_THANH_TOAN".equals(donHang.getThanhToan().getTrangThai())) {
            donHang.getThanhToan().setTrangThai("HOAN_TIEN");
        }

        donHangRepository.save(donHang);

        return "redirect:/orders";
    }
}
