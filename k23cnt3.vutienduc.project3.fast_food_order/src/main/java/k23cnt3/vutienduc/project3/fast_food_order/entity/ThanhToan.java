package k23cnt3.vutienduc.project3.fast_food_order.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "thanh_toan")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThanhToan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double soTien;
    private String phuongThuc; // "ONLINE" hoặc "COD"
    private LocalDateTime ngayThanhToan;
    private String trangThai;

    @OneToOne
    @JoinColumn(name = "don_hang_id")
    private DonHang donHang;
}
