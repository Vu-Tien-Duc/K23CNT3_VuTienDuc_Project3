package k23cnt3.vutienduc.project3.fast_food_order.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "the_loai")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TheLoai {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenTheLoai;

    @OneToMany(mappedBy = "theLoai")
    private List<MonAn> monAns;
}
