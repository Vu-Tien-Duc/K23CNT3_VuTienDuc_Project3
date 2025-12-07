package k23cnt3.vutienduc.project3.fast_food_order.repository;

import k23cnt3.vutienduc.project3.fast_food_order.entity.BinhLuan;
import k23cnt3.vutienduc.project3.fast_food_order.entity.MonAn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BinhLuanRepository extends JpaRepository<BinhLuan, Long> {

    // Tìm bình luận theo món ăn
    List<BinhLuan> findByMonAn(MonAn monAn);

    // Tìm bình luận theo món ăn ID
    List<BinhLuan> findByMonAnId(Long monAnId);

    // Tìm bình luận theo đánh giá
    List<BinhLuan> findByDanhGia(int danhGia);

    // Tìm bình luận theo tên người dùng (contains)
    List<BinhLuan> findByNguoiDungTenContainingIgnoreCase(String keyword);

    // Tính đánh giá trung bình của một món ăn
    @Query("SELECT AVG(b.danhGia) FROM BinhLuan b WHERE b.monAn.id = :monAnId")
    Double getAverageRatingByMonAnId(Long monAnId);

    // Tính đánh giá trung bình tất cả
    @Query("SELECT AVG(b.danhGia) FROM BinhLuan b")
    Double getAverageRating();

    // Đếm số bình luận theo món ăn
    long countByMonAnId(Long monAnId);
}