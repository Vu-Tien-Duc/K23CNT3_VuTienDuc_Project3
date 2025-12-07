package k23cnt3.vutienduc.project3.fast_food_order.repository;

import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface NguoiDungRepository extends JpaRepository<NguoiDung, Long> {

    Optional<NguoiDung> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query("""
        SELECT u FROM NguoiDung u
        WHERE (:keyword = '' OR u.ten LIKE %:keyword% OR u.email LIKE %:keyword%)
        AND (:role = '' OR u.vaiTro.tenVaiTro = :role)
        """)
    Page<NguoiDung> search(
            @Param("keyword") String keyword,
            @Param("role") String role,
            Pageable pageable);
}
