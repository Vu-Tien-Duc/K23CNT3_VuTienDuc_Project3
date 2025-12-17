package k23cnt3.vutienduc.project3.fast_food_order.controller.user;

import jakarta.servlet.http.HttpSession;
import k23cnt3.vutienduc.project3.fast_food_order.entity.*;
import k23cnt3.vutienduc.project3.fast_food_order.repository.*;
import k23cnt3.vutienduc.project3.fast_food_order.service.MonAnService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/checkout")
public class CheckoutUserController extends BaseController {

    private final MonAnService monAnService;
    private final DonHangRepository donHangRepository;
    private final GiamGiaRepository giamGiaRepository;

    public CheckoutUserController(
            NguoiDungRepository nguoiDungRepository,
            MonAnService monAnService,
            DonHangRepository donHangRepository,
            GiamGiaRepository giamGiaRepository
    ) {
        super(nguoiDungRepository);
        this.monAnService = monAnService;
        this.donHangRepository = donHangRepository;
        this.giamGiaRepository = giamGiaRepository;
    }

    /* ================== HIỂN THỊ CHECKOUT ================== */
    @GetMapping
    public String checkoutPage(
            HttpSession session,
            Model model,
            Principal principal
    ) {

        if (principal == null) {
            return "redirect:/login";
        }

        addLoggedUser(model, principal);
        addCartCount(model, session);

        Map<Long, Integer> cart =
                (Map<Long, Integer>) session.getAttribute("CART");

        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        Map<MonAn, Integer> cartItems = new LinkedHashMap<>();
        double tongTien = 0;

        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            MonAn monAn = monAnService.getById(entry.getKey());
            int soLuong = entry.getValue();

            cartItems.put(monAn, soLuong);
            tongTien += monAn.getGia() * soLuong;
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("tongTien", tongTien);

        return "user/checkout/index";
    }

    /* ================== CHECK MÃ GIẢM GIÁ (AJAX) ================== */
    @GetMapping("/check-promo")
    @ResponseBody
    public Map<String, Object> checkPromo(
            @RequestParam String maGiamGia,
            HttpSession session
    ) {

        Map<String, Object> response = new HashMap<>();

        Optional<GiamGia> opt = giamGiaRepository.findByMaGiamGia(maGiamGia.trim());
        if (opt.isEmpty()) {
            response.put("success", false);
            response.put("message", "Mã giảm giá không tồn tại!");
            return response;
        }

        GiamGia giamGia = opt.get();
        LocalDateTime now = LocalDateTime.now();

        if (giamGia.getNgayBatDau().isAfter(now)
                || giamGia.getNgayKetThuc().isBefore(now)) {

            response.put("success", false);
            response.put("message", "Mã giảm giá đã hết hạn!");
            return response;
        }

        Map<Long, Integer> cart =
                (Map<Long, Integer>) session.getAttribute("CART");

        if (cart == null || cart.isEmpty()) {
            response.put("success", false);
            response.put("message", "Giỏ hàng trống!");
            return response;
        }

        double tongTien = 0;
        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            MonAn monAn = monAnService.getById(entry.getKey());
            tongTien += monAn.getGia() * entry.getValue();
        }

        double giam = giamGia.isLaPhanTram()
                ? tongTien * giamGia.getGiaTri() / 100
                : giamGia.getGiaTri();

        double tongTienSauGiam = Math.max(tongTien - giam, 0);

        // ✅ FIX LỖI UNDEFINED
        response.put("success", true);
        response.put("message", "Áp dụng mã giảm giá thành công");
        response.put("tongTienSauGiam", tongTienSauGiam);

        return response;
    }

    /* ================== ĐẶT HÀNG ================== */
    @PostMapping
    public String placeOrder(
            @RequestParam String diaChi,
            @RequestParam String sdt,
            @RequestParam String phuongThuc,
            @RequestParam(required = false) String maGiamGia,
            HttpSession session,
            Principal principal
    ) {

        if (principal == null) {
            return "redirect:/login";
        }

        NguoiDung nguoiDung = nguoiDungRepository
                .findByEmail(principal.getName())
                .orElseThrow();

        Map<Long, Integer> cart =
                (Map<Long, Integer>) session.getAttribute("CART");

        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        DonHang donHang = DonHang.builder()
                .nguoiDung(nguoiDung)
                .ngayDat(LocalDateTime.now())
                .diaChiGiao(diaChi)
                .sdt(sdt)
                .trangThai(TrangThaiDonHang.CHO_XU_LY)
                .build();

        List<ChiTietDonHang> chiTietList = new ArrayList<>();
        double tongTien = 0;

        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            MonAn monAn = monAnService.getById(entry.getKey());

            ChiTietDonHang ct = ChiTietDonHang.builder()
                    .donHang(donHang)
                    .monAn(monAn)
                    .soLuong(entry.getValue())
                    .build();

            chiTietList.add(ct);
            tongTien += monAn.getGia() * entry.getValue();
        }

        donHang.setChiTietDonHangs(chiTietList);

        ThanhToan thanhToan = ThanhToan.builder()
                .donHang(donHang)
                .soTien(tongTien)
                .phuongThuc(phuongThuc)
                .ngayThanhToan(LocalDateTime.now())
                .trangThai("CHUA_THANH_TOAN")
                .build();

        donHang.setThanhToan(thanhToan);
        donHangRepository.save(donHang);

        session.removeAttribute("CART");

        if ("ONLINE".equalsIgnoreCase(phuongThuc)) {
            return "redirect:/payment-online/" + donHang.getId();
        }

        return "redirect:/checkout/success";
    }
    @PostMapping("/buy-now")
    public String buyNow(
            @RequestParam Long monAnId,
            @RequestParam(defaultValue = "1") int soLuong,
            HttpSession session,
            Principal principal
    ) {

        if (principal == null) {
            return "redirect:/login";
        }

        // Tạo giỏ hàng tạm chỉ chứa 1 món
        Map<Long, Integer> cart = new HashMap<>();
        cart.put(monAnId, soLuong);

        // Ghi đè CART hiện tại
        session.setAttribute("CART", cart);

        return "redirect:/checkout";
    }


    /* ================== THÀNH CÔNG ================== */
    @GetMapping("/success")
    public String success(
            Model model,
            Principal principal,
            HttpSession session
    ) {
        addLoggedUser(model, principal);
        addCartCount(model, session);
        return "user/checkout/success";
    }
}
