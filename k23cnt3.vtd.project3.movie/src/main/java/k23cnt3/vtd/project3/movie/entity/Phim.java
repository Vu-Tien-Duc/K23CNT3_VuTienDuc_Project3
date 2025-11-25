package k23cnt3.vtd.project3.movie.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity Phim (Movie)
 */
@Entity
@Table(name = "phim")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Phim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tieuDe;

    @Column(columnDefinition = "TEXT")
    private String moTa;

    private String hinhAnh;

    private Integer namPhatHanh;

    private String linkXem;

    private LocalDateTime ngayTao;

    // Một phim có nhiều thể loại
    @ManyToMany
    @JoinTable(
            name = "phim_the_loai",
            joinColumns = @JoinColumn(name = "phim_id"),
            inverseJoinColumns = @JoinColumn(name = "the_loai_id")
    )
    private Set<TheLoai> theLoais = new HashSet<>();

    // Một phim có nhiều bình luận
    @OneToMany(mappedBy = "phim", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BinhLuan> binhLuans = new HashSet<>();

    // **Transient field để hiển thị rating trung bình**
    @Transient
    private Double avgRating;
}
