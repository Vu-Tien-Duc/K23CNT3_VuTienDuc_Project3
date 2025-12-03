package k23cnt3.vutienduc.project3.fast_food_order.repository;

import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NguoiDungRepository extends JpaRepository<NguoiDung, Long> {
    Optional<NguoiDung> findByEmail(String email);
    Boolean existsByEmail(String email);
}
