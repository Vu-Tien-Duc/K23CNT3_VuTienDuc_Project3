package k23cnt3.vtd.project3.movie.repository;

import k23cnt3.vtd.project3.movie.entity.Phim;
import k23cnt3.vtd.project3.movie.entity.TheLoai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhimRepository extends JpaRepository<Phim, Long> {

    // Lọc phim theo từ khóa trong tiêu đề hoặc mô tả
    @Query("SELECT p FROM Phim p WHERE LOWER(p.tieuDe) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.moTa) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Phim> findByKeyword(String keyword, Pageable pageable);

    // Lọc phim theo thể loại
    @Query("SELECT DISTINCT p FROM Phim p JOIN p.theLoais t WHERE t IN :theLoais")
    Page<Phim> findByTheLoais(List<TheLoai> theLoais, Pageable pageable);

    // Lọc phim theo năm phát hành
    Page<Phim> findByNamPhatHanh(Integer namPhatHanh, Pageable pageable);

    // Lọc phim theo đánh giá trung bình
    @Query("SELECT p FROM Phim p LEFT JOIN p.binhLuans b GROUP BY p.id HAVING AVG(b.danhGia) >= :minRating")
    Page<Phim> findByMinAverageRating(Double minRating, Pageable pageable);
}
