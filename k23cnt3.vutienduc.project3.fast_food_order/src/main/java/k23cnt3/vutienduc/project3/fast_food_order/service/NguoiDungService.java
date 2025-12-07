package k23cnt3.vutienduc.project3.fast_food_order.service;

import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import k23cnt3.vutienduc.project3.fast_food_order.entity.VaiTro;
import k23cnt3.vutienduc.project3.fast_food_order.repository.NguoiDungRepository;
import k23cnt3.vutienduc.project3.fast_food_order.repository.VaiTroRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NguoiDungService {

    private final NguoiDungRepository nguoiDungRepository;
    private final VaiTroRepository vaiTroRepository;
    private final PasswordEncoder passwordEncoder;

    public long countAll() {
        return nguoiDungRepository.count();
    }
    // =================== USER - SELF PROFILE ===================
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

    public void changePassword(String oldPass, String newPass) {
        NguoiDung current = getCurrentUser();

        if (!passwordEncoder.matches(oldPass, current.getMatKhau())) {
            throw new RuntimeException("Mật khẩu cũ không đúng");
        }

        current.setMatKhau(passwordEncoder.encode(newPass));
        nguoiDungRepository.save(current);
    }

    // =================== ADMIN FUNCTIONS ===================

    public NguoiDung getById(Long id) {
        return nguoiDungRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
    }

    public NguoiDung createUser(NguoiDung u) {

        if (u.getVaiTro() == null) {
            VaiTro role = vaiTroRepository.findByTenVaiTro("USER")
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy role USER"));
            u.setVaiTro(role);
        }

        u.setMatKhau(passwordEncoder.encode(u.getMatKhau()));
        return nguoiDungRepository.save(u);
    }

    public NguoiDung updateUserByAdmin(NguoiDung u) {
        NguoiDung old = getById(u.getId());

        old.setTen(u.getTen());
        old.setEmail(u.getEmail());
        old.setSdt(u.getSdt());
        old.setDiaChi(u.getDiaChi());
        old.setVaiTro(u.getVaiTro());

        return nguoiDungRepository.save(old);
    }

    public void deleteUser(Long id) {
        NguoiDung u = getById(id);

        if (!u.getDonHangs().isEmpty()) {
            throw new RuntimeException("Không thể xóa user đã có đơn hàng.");
        }

        nguoiDungRepository.delete(u);
    }

    public Page<NguoiDung> searchUsers(String keyword, String role, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        keyword = (keyword == null) ? "" : keyword.trim();
        role = (role == null || role.equals("ALL")) ? "" : role;

        return nguoiDungRepository.search(keyword, role, pageable);
    }

    public List<VaiTro> getAllRoles() {
        return vaiTroRepository.findAll();
    }
}
