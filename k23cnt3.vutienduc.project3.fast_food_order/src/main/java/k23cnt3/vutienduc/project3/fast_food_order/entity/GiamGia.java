package k23cnt3.vutienduc.project3.fast_food_order.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "giam_gia")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiamGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String maGiamGia;
    private double giaTri;
    private boolean laPhanTram;

    private LocalDateTime ngayBatDau; // ngày bắt đầu có hiệu lực
    private LocalDateTime ngayKetThuc; // ngày kết thúc

    @OneToMany(mappedBy = "giamGia")
    private List<DonHang> donHangs;
}
