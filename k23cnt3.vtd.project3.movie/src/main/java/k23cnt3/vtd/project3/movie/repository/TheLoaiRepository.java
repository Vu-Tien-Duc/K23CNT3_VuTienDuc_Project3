package k23cnt3.vtd.project3.movie.repository;

import k23cnt3.vtd.project3.movie.entity.TheLoai;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TheLoaiRepository extends JpaRepository<TheLoai, Long> {
    Optional<TheLoai> findByTen(String ten);
}
