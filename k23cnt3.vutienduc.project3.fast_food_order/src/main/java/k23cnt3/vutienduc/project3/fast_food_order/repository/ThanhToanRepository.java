package k23cnt3.vutienduc.project3.fast_food_order.repository;

import k23cnt3.vutienduc.project3.fast_food_order.entity.ThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ThanhToanRepository extends JpaRepository<ThanhToan, Long> {

    // ===== DETAIL PAGE =====
    @Query("""
        SELECT new map(
            t.id as id,
            t.soTien as soTien,
            t.phuongThuc as phuongThuc,
            t.trangThai as trangThai,
            t.ngayThanhToan as ngayThanhToan,
            t.donHang.id as donHangId
        )
        FROM ThanhToan t
        WHERE t.id = :id
    """)
    Optional<Map<String, Object>> findByIdForEdit(@Param("id") Long id);

    // ===== FILTER =====
    @Query("""
        SELECT t
        FROM ThanhToan t
        WHERE (:phuongThuc IS NULL OR t.phuongThuc = :phuongThuc)
          AND (:trangThai IS NULL OR t.trangThai = :trangThai)
          AND (:fromDate IS NULL OR t.ngayThanhToan >= :fromDate)
        ORDER BY t.ngayThanhToan DESC
    """)
    List<ThanhToan> filterThanhToan(
            @Param("phuongThuc") String phuongThuc,
            @Param("trangThai") String trangThai,
            @Param("fromDate") LocalDateTime fromDate
    );

    // ===== TỔNG DOANH THU (ĐÃ THANH TOÁN) =====
    @Query("""
        SELECT COALESCE(SUM(t.soTien), 0)
        FROM ThanhToan t
        WHERE t.trangThai = 'DA_THANH_TOAN'
    """)
    Double sumRevenueDaThanhToan();

    // ===== ĐẾM THEO PHƯƠNG THỨC + ĐÃ THANH TOÁN =====
    @Query("""
        SELECT COUNT(t)
        FROM ThanhToan t
        WHERE t.phuongThuc = :method
          AND t.trangThai = 'DA_THANH_TOAN'
    """)
    long countByMethodDaThanhToan(@Param("method") String method);
}
