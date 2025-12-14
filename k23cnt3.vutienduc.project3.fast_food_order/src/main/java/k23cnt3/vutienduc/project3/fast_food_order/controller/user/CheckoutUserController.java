package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import jakarta.servlet.http.HttpSession;
import k23cnt3.vutienduc.project3.fast_food_order.entity.*;
import k23cnt3.vutienduc.project3.fast_food_order.repository.*;
import k23cnt3.vutienduc.project3.fast_food_order.service.MonAnService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutUserController {

    private final MonAnService monAnService;
    private final NguoiDungRepository nguoiDungRepository;
    private final DonHangRepository donHangRepository;
    private final GiamGiaRepository giamGiaRepository;

    // ================== HIỂN THỊ TRANG CHECKOUT ==================
    @GetMapping
    public String checkoutPage(HttpSession session,
                               Model model,
                               Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        NguoiDung nguoiDung = nguoiDungRepository
                .findByEmail(principal.getName())
                .orElse(null);

        Map<Long, Integer> cart =
                (Map<Long, Integer>) session.getAttribute("CART");

        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        Map<MonAn, Integer> cartItems = new LinkedHashMap<>();
        double tongTien = 0;
        int cartCount = 0;

        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            MonAn monAn = monAnService.getById(entry.getKey());
            int soLuong = entry.getValue();

            cartItems.put(monAn, soLuong);
            tongTien += monAn.getGia() * soLuong;
            cartCount += soLuong;
        }

        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("tongTien", tongTien);
        model.addAttribute("cartCount", cartCount);

        return "user/checkout/index";
    }

    // ================== XỬ LÝ ĐẶT HÀNG ==================
    @PostMapping
    public String placeOrder(@RequestParam String diaChi,
                             @RequestParam String sdt,
                             @RequestParam String phuongThuc,
                             @RequestParam(required = false) String maGiamGia,
                             HttpSession session,
                             Principal principal,
                             Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        NguoiDung nguoiDung = nguoiDungRepository
                .findByEmail(principal.getName())
                .orElse(null);

        Map<Long, Integer> cart =
                (Map<Long, Integer>) session.getAttribute("CART");

        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        // ================== TẠO ĐƠN HÀNG ==================
        DonHang donHang = DonHang.builder()
                .nguoiDung(nguoiDung)
                .ngayDat(LocalDateTime.now())
                .diaChiGiao(diaChi)
                .sdt(sdt)
                .trangThai(TrangThaiDonHang.CHO_XU_LY)
                .build();

        // ================== KIỂM TRA MÃ GIẢM GIÁ ==================
        GiamGia giamGia = null;

        if (maGiamGia != null && !maGiamGia.trim().isEmpty()) {
            Optional<GiamGia> opt = giamGiaRepository.findByMaGiamGia(maGiamGia.trim());

            if (opt.isEmpty()) {
                model.addAttribute("error", "Mã giảm giá không tồn tại!");
                return checkoutPage(session, model, principal);
            }

            giamGia = opt.get();

            LocalDateTime now = LocalDateTime.now();
            if (giamGia.getNgayBatDau().isAfter(now) || giamGia.getNgayKetThuc().isBefore(now)) {
                model.addAttribute("error", "Mã giảm giá đã hết hạn!");
                return checkoutPage(session, model, principal);
            }

            donHang.setGiamGia(giamGia);
        }

        // ================== TẠO CHI TIẾT ĐƠN HÀNG ==================
        List<ChiTietDonHang> chiTietDonHangs = new ArrayList<>();
        double tongTien = 0;

        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            MonAn monAn = monAnService.getById(entry.getKey());
            int soLuong = entry.getValue();

            ChiTietDonHang ct = ChiTietDonHang.builder()
                    .donHang(donHang)
                    .monAn(monAn)
                    .soLuong(soLuong)
                    .build();

            chiTietDonHangs.add(ct);
            tongTien += monAn.getGia() * soLuong;
        }

        donHang.setChiTietDonHangs(chiTietDonHangs);

        // ================== TÍNH TIỀN SAU GIẢM GIÁ ==================
        if (giamGia != null) {
            if (giamGia.isLaPhanTram()) {
                tongTien -= tongTien * giamGia.getGiaTri() / 100;
            } else {
                tongTien -= giamGia.getGiaTri();
            }
            if (tongTien < 0) tongTien = 0;
        }

        // ================== TẠO THANH TOÁN ==================
        ThanhToan thanhToan = ThanhToan.builder()
                .donHang(donHang)
                .soTien(tongTien)
                .phuongThuc(phuongThuc)
                .ngayThanhToan(LocalDateTime.now())
                .trangThai(
                        phuongThuc.equalsIgnoreCase("COD")
                                ? "CHUA_THANH_TOAN"
                                : "CHUA_THANH_TOAN" // Online chưa thanh toán thực tế
                )
                .build();

        donHang.setThanhToan(thanhToan);

        // ================== LƯU ==================
        donHangRepository.save(donHang);

        // ================== CLEAR CART ==================
        session.removeAttribute("CART");

        // ================== CHUYỂN HƯỚNG THEO PHƯƠNG THỨC ==================
        if (phuongThuc.equalsIgnoreCase("ONLINE")) {
            return "redirect:/payment-online/" + donHang.getId();
        } else {
            return "redirect:/checkout/success";
        }
    }

    // ================== TRANG THÀNH CÔNG ==================
    // ================== TRANG THÀNH CÔNG ==================
    @GetMapping("/success")
    public String successPage(HttpSession session,
                              Model model,
                              Principal principal) {

        if (principal != null) {
            NguoiDung nguoiDung = nguoiDungRepository
                    .findByEmail(principal.getName())
                    .orElse(null);
            model.addAttribute("nguoiDung", nguoiDung);
        }

        return "user/checkout/success";
    }

}
