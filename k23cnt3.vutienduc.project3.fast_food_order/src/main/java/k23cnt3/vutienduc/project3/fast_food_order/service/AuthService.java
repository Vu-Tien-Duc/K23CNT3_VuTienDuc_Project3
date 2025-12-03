package k23cnt3.vutienduc.project3.fast_food_order.service;

import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import k23cnt3.vutienduc.project3.fast_food_order.entity.VaiTro;
import k23cnt3.vutienduc.project3.fast_food_order.repository.NguoiDungRepository;
import k23cnt3.vutienduc.project3.fast_food_order.repository.VaiTroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final NguoiDungRepository nguoiDungRepository;
    private final VaiTroRepository vaiTroRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    // Đăng ký người dùng
    public NguoiDung register(NguoiDung nguoiDung) {
        if (nguoiDungRepository.existsByEmail(nguoiDung.getEmail())) {
            throw new RuntimeException("Email đã được sử dụng!");
        }

        if (nguoiDung.getMatKhau() == null || nguoiDung.getMatKhau().length() < 6) {
            throw new RuntimeException("Mật khẩu phải có ít nhất 6 ký tự");
        }

        nguoiDung.setMatKhau(passwordEncoder.encode(nguoiDung.getMatKhau()));

        VaiTro userRole = vaiTroRepository.findByTenVaiTro("USER")
                .orElseGet(() -> {
                    VaiTro role = new VaiTro();
                    role.setTenVaiTro("USER");
                    return vaiTroRepository.save(role);
                });

        nguoiDung.setVaiTro(userRole);
        return nguoiDungRepository.save(nguoiDung);
    }

    // Xác thực login (session)
    public NguoiDung login(String email, String matKhau) {
        if (email == null || email.isEmpty() || matKhau == null || matKhau.isEmpty()) {
            throw new RuntimeException("Email hoặc mật khẩu không được để trống");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, matKhau)
        );

        // Nếu không exception, login thành công
        return nguoiDungRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
    }
}
