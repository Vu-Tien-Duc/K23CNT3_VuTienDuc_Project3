    package k23cnt3.vutienduc.project3.fast_food_order.config;

    import k23cnt3.vutienduc.project3.fast_food_order.entity.*;
    import k23cnt3.vutienduc.project3.fast_food_order.repository.*;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.boot.CommandLineRunner;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Component;

    import java.time.LocalDateTime;
    import java.util.Arrays;

    @Component
    @RequiredArgsConstructor
    @Slf4j
    public class DataInitializer implements CommandLineRunner {

        private final VaiTroRepository vaiTroRepository;
        private final NguoiDungRepository nguoiDungRepository;
        private final TheLoaiRepository theLoaiRepository;
        private final MonAnRepository monAnRepository;
        private final GiamGiaRepository giamGiaRepository;
        private final PasswordEncoder passwordEncoder;

        @Override
        public void run(String... args) throws Exception {
            log.info("🚀 Bắt đầu khởi tạo dữ liệu...");

            // Tạo vai trò
            if (vaiTroRepository.count() == 0) {
                VaiTro userRole = new VaiTro();
                userRole.setTenVaiTro("USER");
                vaiTroRepository.save(userRole);

                VaiTro adminRole = new VaiTro();
                adminRole.setTenVaiTro("ADMIN");
                vaiTroRepository.save(adminRole);

                log.info("✅ Đã tạo vai trò USER và ADMIN");
            }

            // Tạo admin
            if (!nguoiDungRepository.existsByEmail("admin@food.com")) {
                VaiTro adminRole = vaiTroRepository.findByTenVaiTro("ADMIN").orElseThrow();

                NguoiDung admin = new NguoiDung();
                admin.setTen("Administrator");
                admin.setEmail("admin@food.com");
                admin.setMatKhau(passwordEncoder.encode("admin123"));
                admin.setSdt("0900000000");
                admin.setDiaChi("Hà Nội");
                admin.setVaiTro(adminRole);
                nguoiDungRepository.save(admin);

                log.info("✅ Admin: admin@food.com / admin123");
            }

            // Tạo user
            if (!nguoiDungRepository.existsByEmail("user@food.com")) {
                VaiTro userRole = vaiTroRepository.findByTenVaiTro("USER").orElseThrow();

                NguoiDung user = new NguoiDung();
                user.setTen("Nguyễn Văn A");
                user.setEmail("user@food.com");
                user.setMatKhau(passwordEncoder.encode("user123"));
                user.setSdt("0911111111");
                user.setDiaChi("TP.HCM");
                user.setVaiTro(userRole);
                nguoiDungRepository.save(user);

                log.info("✅ User: user@food.com / user123");
            }

            // Tạo thể loại
            if (theLoaiRepository.count() == 0) {
                TheLoai tl1 = new TheLoai();
                tl1.setTenTheLoai("Burger");

                TheLoai tl2 = new TheLoai();
                tl2.setTenTheLoai("Pizza");

                TheLoai tl3 = new TheLoai();
                tl3.setTenTheLoai("Gà Rán");

                TheLoai tl4 = new TheLoai();
                tl4.setTenTheLoai("Đồ Uống");

                theLoaiRepository.saveAll(Arrays.asList(tl1, tl2, tl3, tl4));
                log.info("✅ Đã tạo 4 thể loại");
            }

            // Tạo món ăn
            if (monAnRepository.count() == 0) {
                TheLoai burger = theLoaiRepository.findAll().get(0);
                TheLoai pizza = theLoaiRepository.findAll().get(1);

                MonAn m1 = new MonAn();
                m1.setTen("Burger Bò Phô Mai");
                m1.setMoTa("Burger bò ngon");
                m1.setGia(65000.0);
                m1.setHinhAnh(Arrays.asList("https://via.placeholder.com/300"));
                m1.setTheLoai(burger);

                MonAn m2 = new MonAn();
                m2.setTen("Pizza Hải Sản");
                m2.setMoTa("Pizza hải sản tươi");
                m2.setGia(149000.0);
                m2.setHinhAnh(Arrays.asList("https://via.placeholder.com/300"));
                m2.setTheLoai(pizza);

                monAnRepository.saveAll(Arrays.asList(m1, m2));
                log.info("✅ Đã tạo món ăn");
            }

            // Tạo mã giảm giá
            if (giamGiaRepository.count() == 0) {
                GiamGia gg1 = new GiamGia();
                gg1.setMaGiamGia("WELCOME10");
                gg1.setGiaTri(10.0);
                gg1.setLaPhanTram(true);
                gg1.setNgayBatDau(LocalDateTime.now());
                gg1.setNgayKetThuc(LocalDateTime.now().plusMonths(1));

                giamGiaRepository.save(gg1);
                log.info("✅ Mã giảm giá: WELCOME10");
            }

            log.info("\n🎉 Khởi tạo hoàn tất!");
        }
    }