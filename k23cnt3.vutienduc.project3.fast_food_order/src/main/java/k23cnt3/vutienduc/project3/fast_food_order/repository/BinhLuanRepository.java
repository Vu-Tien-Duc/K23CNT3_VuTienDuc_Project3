package k23cnt3.vutienduc.project3.fast_food_order.repository;

import k23cnt3.vutienduc.project3.fast_food_order.entity.BinhLuan;
import k23cnt3.vutienduc.project3.fast_food_order.entity.MonAn;
import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BinhLuanRepository extends JpaRepository<BinhLuan, Long> {
    List<BinhLuan> findByMonAn(MonAn monAn);
    List<BinhLuan> findByMonAnOrderByNgayTaoDesc(MonAn monAn);
    List<BinhLuan> findByNguoiDungOrderByNgayTaoDesc(NguoiDung nguoiDung);
    List<BinhLuan> findAllByOrderByNgayTaoDesc();
}