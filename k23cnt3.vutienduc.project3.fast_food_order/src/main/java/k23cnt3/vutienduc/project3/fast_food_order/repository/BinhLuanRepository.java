package k23cnt3.vutienduc.project3.fast_food_order.repository;

import k23cnt3.vutienduc.project3.fast_food_order.entity.BinhLuan;
import k23cnt3.vutienduc.project3.fast_food_order.entity.MonAn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BinhLuanRepository extends JpaRepository<BinhLuan, Long> {

    // Lấy bình luận theo món ăn (entity)
    List<BinhLuan> findByMonAn(MonAn monAn);

    // Lấy bình luận theo món ăn ID
    List<BinhLuan> findByMonAnId(Long monAnId);

    // Lấy bình luận theo món ăn và sắp xếp theo ngày tạo (DÙNG CHO DETAIL)
    List<BinhLuan> findByMonAnIdOrderByNgayTaoDesc(Long monAnId);

    // Lấy bình luận theo rating
    List<BinhLuan> findByDanhGia(int danhGia);

    // Lọc theo món ăn + rating
    List<BinhLuan> findByMonAnIdAndDanhGia(Long monAnId, int danhGia);

    // Lọc theo tên người dùng (like)
    List<BinhLuan> findByNguoiDungTenContainingIgnoreCase(String keyword);

    // Tìm bằng keyword tuỳ biến
    @Query("""
           SELECT b FROM BinhLuan b 
           WHERE LOWER(b.nguoiDung.ten) LIKE %:keyword%
           """)
    List<BinhLuan> searchByKeyword(@Param("keyword") String keyword);

    // Rating trung bình theo món
    @Query("SELECT AVG(b.danhGia) FROM BinhLuan b WHERE b.monAn.id = :monAnId")
    Double getAverageRatingByMonAnId(@Param("monAnId") Long monAnId);

    // Rating trung bình tất cả
    @Query("SELECT AVG(b.danhGia) FROM BinhLuan b")
    Double getAverageRating();

    // Đếm số bình luận của món ăn
    long countByMonAnId(Long monAnId);
}
