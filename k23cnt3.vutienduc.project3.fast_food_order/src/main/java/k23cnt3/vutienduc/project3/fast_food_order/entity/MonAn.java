package k23cnt3.vutienduc.project3.fast_food_order.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mon_an")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonAn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ten;
    private String moTa;
    private double gia;

    @ElementCollection
    @CollectionTable(name = "mon_an_hinh_anh", joinColumns = @JoinColumn(name = "mon_an_id"))
    @Column(name = "url_hinh")
    private List<String> hinhAnh = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "the_loai_id")
    private TheLoai theLoai;

    @OneToMany(mappedBy = "monAn")
    private List<BinhLuan> binhLuans;

    @OneToMany(mappedBy = "monAn")
    private List<ChiTietDonHang> chiTietDonHangs;
}
