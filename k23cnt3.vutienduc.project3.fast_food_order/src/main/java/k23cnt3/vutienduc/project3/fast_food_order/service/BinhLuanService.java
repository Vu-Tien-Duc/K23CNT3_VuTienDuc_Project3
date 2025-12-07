package k23cnt3.vutienduc.project3.fast_food_order.service;

import k23cnt3.vutienduc.project3.fast_food_order.entity.BinhLuan;
import k23cnt3.vutienduc.project3.fast_food_order.repository.BinhLuanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BinhLuanService {

    private final BinhLuanRepository binhLuanRepository;

    // Lấy tất cả bình luận
    public List<BinhLuan> findAll() {
        return binhLuanRepository.findAll();
    }

    // Lấy bình luận theo ID
    public Optional<BinhLuan> findById(Long id) {
        return binhLuanRepository.findById(id);
    }

    // Lọc bình luận (kết hợp nhiều điều kiện)
    public List<BinhLuan> filterBinhLuan(Long monAnId, Integer rating, String keyword) {
        List<BinhLuan> result = findAll();

        // Lọc theo món ăn
        if (monAnId != null) {
            result = binhLuanRepository.findByMonAnId(monAnId);
        }

        // Lọc theo rating
        if (rating != null) {
            result = result.stream()
                    .filter(bl -> bl.getDanhGia() == rating)
                    .toList();
        }

        // Lọc theo keyword (tên người dùng)
        if (keyword != null && !keyword.trim().isEmpty()) {
            result = result.stream()
                    .filter(bl -> bl.getNguoiDung().getTen()
                            .toLowerCase()
                            .contains(keyword.toLowerCase()))
                    .toList();
        }

        return result;
    }

    // Lưu bình luận
    public BinhLuan save(BinhLuan binhLuan) {
        return binhLuanRepository.save(binhLuan);
    }

    // Xóa bình luận
    public void deleteById(Long id) {
        binhLuanRepository.deleteById(id);
    }

    // Tính đánh giá trung bình
    public Double getAverageRating() {
        Double avg = binhLuanRepository.getAverageRating();
        return avg != null ? avg : 0.0;
    }

    // Tính đánh giá trung bình theo món ăn
    public Double getAverageRatingByMonAn(Long monAnId) {
        Double avg = binhLuanRepository.getAverageRatingByMonAnId(monAnId);
        return avg != null ? avg : 0.0;
    }

    // Đếm tất cả
    public long countAll() {
        return binhLuanRepository.count();
    }

    // Đếm theo món ăn
    public long countByMonAn(Long monAnId) {
        return binhLuanRepository.countByMonAnId(monAnId);
    }
}