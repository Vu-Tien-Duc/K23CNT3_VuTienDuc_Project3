package k23cnt3.vtd.project3.movie;

import k23cnt3.vtd.project3.movie.entity.NguoiDung;
import k23cnt3.vtd.project3.movie.service.NguoiDungService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("Ứng dụng đã khởi động!");
    }

    // Tạo admin mặc định khi ứng dụng chạy
    @Bean
    CommandLineRunner initAdmin(NguoiDungService nguoiDungService, PasswordEncoder passwordEncoder) {
        return args -> {
            if (nguoiDungService.findByTenDangNhap("admin").isEmpty()) {
                NguoiDung admin = NguoiDung.builder()
                        .tenDangNhap("admin")
                        .matKhau(passwordEncoder.encode("123456"))
                        .email("admin@example.com")
                        .vaiTro(NguoiDung.VaiTro.ADMIN)
                        .build();
                nguoiDungService.save(admin);
                System.out.println("Admin mặc định đã được tạo: admin / 123456");
            }
        };
    }
}
