package k23cnt3.vtd.project3.movie.repository;

import k23cnt3.vtd.project3.movie.entity.BinhLuan;
import k23cnt3.vtd.project3.movie.entity.Phim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BinhLuanRepository extends JpaRepository<BinhLuan, Long> {
    List<BinhLuan> findByPhimOrderByNgayTaoDesc(Phim phim);

    // Tính trung bình đánh giá của một phim
    @Query("SELECT AVG(b.danhGia) FROM BinhLuan b WHERE b.phim.id = :phimId")
    Double findAverageRatingByPhimId(Long phimId);
}
