    package k23cnt3.vutienduc.project3.fast_food_order.service;

    import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
    import k23cnt3.vutienduc.project3.fast_food_order.repository.NguoiDungRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class NguoiDungService {

        private final NguoiDungRepository nguoiDungRepository;
        private final PasswordEncoder passwordEncoder;

        private NguoiDung getCurrentUser() {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            return nguoiDungRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        }

        public NguoiDung getProfile() {
            return getCurrentUser();
        }

        public NguoiDung updateProfile(NguoiDung nguoiDung) {
            NguoiDung current = getCurrentUser();
            current.setTen(nguoiDung.getTen());
            current.setSdt(nguoiDung.getSdt());
            current.setDiaChi(nguoiDung.getDiaChi());
            return nguoiDungRepository.save(current);
        }

        public void changePassword(String matKhauCu, String matKhauMoi) {
            NguoiDung current = getCurrentUser();
            if (!passwordEncoder.matches(matKhauCu, current.getMatKhau())) {
                throw new RuntimeException("Mật khẩu cũ không đúng");
            }
            current.setMatKhau(passwordEncoder.encode(matKhauMoi));
            nguoiDungRepository.save(current);
        }

        public List<NguoiDung> getAllUsers() {
            return nguoiDungRepository.findAll();
        }

        public void deleteUser(Long id) {
            NguoiDung nguoiDung = nguoiDungRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
            if (!nguoiDung.getDonHangs().isEmpty()) {
                throw new RuntimeException("Không thể xóa user đã có đơn hàng");
            }
            nguoiDungRepository.delete(nguoiDung);
        }

    }
