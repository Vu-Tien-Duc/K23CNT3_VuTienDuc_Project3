package k23cnt3.vutienduc.project3.fast_food_order.repository;

import k23cnt3.vutienduc.project3.fast_food_order.entity.GiamGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GiamGiaRepository extends JpaRepository<GiamGia, Long> {
    Optional<GiamGia> findByMaGiamGia(String maGiamGia);
}