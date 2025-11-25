package k23cnt3.vtd.project3.movie.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity TheLoai (Category)
 */
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

    @Column(unique = true, nullable = false)
    private String ten;

    // Một thể loại có nhiều phim (Many-to-Many)
    @ManyToMany(mappedBy = "theLoais")
    private Set<Phim> phims = new HashSet<>();
}
