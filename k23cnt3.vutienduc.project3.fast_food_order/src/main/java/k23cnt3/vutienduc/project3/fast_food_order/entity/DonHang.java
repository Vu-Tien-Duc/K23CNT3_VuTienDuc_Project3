    package k23cnt3.vutienduc.project3.fast_food_order.entity;

    import jakarta.persistence.*;
    import lombok.*;
    import java.time.LocalDateTime;
    import java.util.List;

    @Entity
    @Table(name = "don_hang")
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor

    public class DonHang {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private LocalDateTime ngayDat;
        private String diaChiGiao;
        private String sdt;

        @Enumerated(EnumType.STRING)
        private TrangThaiDonHang trangThai;

        @ManyToOne
        @JoinColumn(name = "nguoi_dung_id")
        private NguoiDung nguoiDung;

        @OneToMany(mappedBy = "donHang", cascade = CascadeType.ALL)
        private List<ChiTietDonHang> chiTietDonHangs;

        @OneToOne(mappedBy = "donHang", cascade = CascadeType.ALL)
        private ThanhToan thanhToan;

        @ManyToOne
        @JoinColumn(name = "giam_gia_id")
        private GiamGia giamGia;

        public double getTongTien() {
            return chiTietDonHangs.stream()
                    .mapToDouble(ct -> ct.getMonAn().getGia() * ct.getSoLuong())
                    .sum();
        }
    }
