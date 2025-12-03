package k23cnt3.vutienduc.project3.fast_food_order.repository;

import k23cnt3.vutienduc.project3.fast_food_order.entity.ThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ThanhToanRepository extends JpaRepository<ThanhToan, Long> {
    @Query("SELECT SUM(t.soTien) FROM ThanhToan t WHERE t.trangThai = 'SUCCESS' OR t.trangThai = 'PENDING'")
    Double sumAllSoTien();
}