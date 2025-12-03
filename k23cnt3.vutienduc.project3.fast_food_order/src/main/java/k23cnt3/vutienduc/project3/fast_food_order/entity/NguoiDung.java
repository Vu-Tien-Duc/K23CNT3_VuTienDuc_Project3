package k23cnt3.vutienduc.project3.fast_food_order.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "nguoi_dung")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NguoiDung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ten;
    private String email;
    private String matKhau;
    private String sdt;
    private String diaChi;

    @ManyToOne
    @JoinColumn(name = "vai_tro_id")
    private VaiTro vaiTro;

    @OneToMany(mappedBy = "nguoiDung")
    private List<DonHang> donHangs;

    @OneToMany(mappedBy = "nguoiDung")
    private List<BinhLuan> binhLuans;
}
