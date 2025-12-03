package k23cnt3.vutienduc.project3.fast_food_order.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "vai_tro")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VaiTro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String tenVaiTro; // "USER" hoặc "ADMIN"

    @OneToMany(mappedBy = "vaiTro")
    private List<NguoiDung> nguoiDungs;
}
