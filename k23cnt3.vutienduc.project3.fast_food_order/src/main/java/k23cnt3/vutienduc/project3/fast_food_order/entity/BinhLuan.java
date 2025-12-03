package k23cnt3.vutienduc.project3.fast_food_order.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "binh_luan")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BinhLuan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String noiDung;
    private int danhGia; // 1-5 sao

    private LocalDateTime ngayTao; // thêm trường ngày tạo

    @ManyToOne
    @JoinColumn(name = "nguoi_dung_id")
    private NguoiDung nguoiDung;

    @ManyToOne
    @JoinColumn(name = "mon_an_id")
    private MonAn monAn;

    // Nếu muốn tự động set ngayTao khi persist:
    @PrePersist
    public void prePersist() {
        this.ngayTao = LocalDateTime.now();
    }
}
