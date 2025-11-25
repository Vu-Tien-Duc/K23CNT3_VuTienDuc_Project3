package k23cnt3.vtd.project3.movie.config;

import k23cnt3.vtd.project3.movie.entity.NguoiDung;
import k23cnt3.vtd.project3.movie.repository.NguoiDungRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service tùy chỉnh cho Spring Security, load user từ DB
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final NguoiDungRepository nguoiDungRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NguoiDung user = nguoiDungRepository.findByTenDangNhap(username)
                .orElseThrow(() -> new UsernameNotFoundException("Người dùng không tồn tại"));

        return User.builder()
                .username(user.getTenDangNhap())
                .password(user.getMatKhau())
                .roles(user.getVaiTro().name()) // ADMIN / USER
                .build();
    }
}
