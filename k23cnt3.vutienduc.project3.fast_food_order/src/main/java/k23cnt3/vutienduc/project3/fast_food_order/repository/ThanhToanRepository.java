package k23cnt3.vutienduc.project3.fast_food_order.repository;

import k23cnt3.vutienduc.project3.fast_food_order.entity.ThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface ThanhToanRepository extends JpaRepository<ThanhToan, Long> {

    // ✅ Query cho detail page
    @Query("SELECT new map(t.id as id, t.soTien as soTien, t.phuongThuc as phuongThuc, " +
            "t.trangThai as trangThai, t.ngayThanhToan as ngayThanhToan, t.donHang.id as donHangId) " +
            "FROM ThanhToan t WHERE t.id = :id")
    Optional<Map<String, Object>> findByIdForEdit(@Param("id") Long id);
}