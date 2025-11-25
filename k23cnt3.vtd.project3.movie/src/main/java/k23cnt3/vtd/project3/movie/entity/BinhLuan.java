package k23cnt3.vtd.project3.movie.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Entity BinhLuan (Comment + Rating)
 */
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

    @Column(columnDefinition = "TEXT")
    private String noiDung;

    @Column(nullable = false)
    private Integer danhGia; // 1-5

    private LocalDateTime ngayTao;

    // NguoiDung viết bình luận
    @ManyToOne
    @JoinColumn(name = "nguoi_dung_id", nullable = false)
    private NguoiDung nguoiDung;

    // Bình luận thuộc về phim nào
    @ManyToOne
    @JoinColumn(name = "phim_id", nullable = false)
    private Phim phim;
}
