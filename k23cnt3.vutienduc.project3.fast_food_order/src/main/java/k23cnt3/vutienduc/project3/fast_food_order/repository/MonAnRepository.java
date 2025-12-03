package k23cnt3.vutienduc.project3.fast_food_order.repository;

import k23cnt3.vutienduc.project3.fast_food_order.entity.MonAn;
import k23cnt3.vutienduc.project3.fast_food_order.entity.TheLoai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonAnRepository extends JpaRepository<MonAn, Long> {
    Page<MonAn> findByTheLoai(TheLoai theLoai, Pageable pageable);
    List<MonAn> findByTheLoai(TheLoai theLoai);
    Page<MonAn> findByTenContainingIgnoreCase(String ten, Pageable pageable);
}