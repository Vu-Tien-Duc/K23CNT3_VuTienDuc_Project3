package k23cnt3.vutienduc.project3.fast_food_order.repository;

import k23cnt3.vutienduc.project3.fast_food_order.entity.DonHang;
import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import k23cnt3.vutienduc.project3.fast_food_order.entity.TrangThaiDonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Long> {
    List<DonHang> findByNguoiDungOrderByNgayDatDesc(NguoiDung nguoiDung);
    List<DonHang> findByTrangThaiOrderByNgayDatDesc(TrangThaiDonHang trangThai);
    List<DonHang> findAllByOrderByNgayDatDesc();
    long countByTrangThai(TrangThaiDonHang trangThai);


    // ===== TỔNG DOANH THU =====
    @Query("""
        SELECT COALESCE(SUM(ct.soLuong * m.gia), 0)
        FROM ChiTietDonHang ct
        JOIN ct.monAn m
        JOIN ct.donHang d
    """)
    Double tongDoanhThu();

    // ===== DOANH THU HÔM NAY =====
    @Query("""
        SELECT COALESCE(SUM(ct.soLuong * m.gia), 0)
        FROM ChiTietDonHang ct
        JOIN ct.monAn m
        JOIN ct.donHang d
        WHERE DATE(d.ngayDat) = CURRENT_DATE
    """)
    Double doanhThuHomNay();
}