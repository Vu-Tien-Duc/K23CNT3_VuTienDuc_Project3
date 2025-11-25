package k23cnt3.vtd.project3.movie.service;

import k23cnt3.vtd.project3.movie.entity.NguoiDung;
import k23cnt3.vtd.project3.movie.repository.NguoiDungRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service quản lý người dùng
 */
@Service
@RequiredArgsConstructor
public class NguoiDungService {

    private final NguoiDungRepository nguoiDungRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Lấy tất cả người dùng
     */
    public List<NguoiDung> findAll() {
        return nguoiDungRepository.findAll();
    }
    public Optional<NguoiDung> findById(Long id) {
        return nguoiDungRepository.findById(id);
    }

    /**
     * Đăng ký người dùng mới (mật khẩu mã hóa, vai trò USER mặc định)
     */
    public NguoiDung dangKy(NguoiDung nguoiDung) {
        nguoiDung.setMatKhau(passwordEncoder.encode(nguoiDung.getMatKhau()));
        nguoiDung.setVaiTro(NguoiDung.VaiTro.USER); // mặc định USER
        return nguoiDungRepository.save(nguoiDung);
    }

    /**
     * Tìm người dùng theo tên đăng nhập
     */
    public Optional<NguoiDung> findByTenDangNhap(String tenDangNhap) {
        return nguoiDungRepository.findByTenDangNhap(tenDangNhap);
    }

    /**
     * Lưu/update người dùng
     */
    public NguoiDung save(NguoiDung nguoiDung) {
        return nguoiDungRepository.save(nguoiDung);
    }

    /**
     * Xóa người dùng theo id
     */
    public void delete(Long id) {
        nguoiDungRepository.deleteById(id);
    }
}
