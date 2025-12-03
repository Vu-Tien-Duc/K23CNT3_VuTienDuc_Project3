package k23cnt3.vutienduc.project3.fast_food_order.repository;

import k23cnt3.vutienduc.project3.fast_food_order.entity.DonHang;
import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import k23cnt3.vutienduc.project3.fast_food_order.entity.TrangThaiDonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Long> {
    List<DonHang> findByNguoiDungOrderByNgayDatDesc(NguoiDung nguoiDung);
    List<DonHang> findByTrangThaiOrderByNgayDatDesc(TrangThaiDonHang trangThai);
    List<DonHang> findAllByOrderByNgayDatDesc();
    long countByTrangThai(TrangThaiDonHang trangThai);
}