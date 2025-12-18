package k23cnt3.vutienduc.project3.fast_food_order.service;

import k23cnt3.vutienduc.project3.fast_food_order.entity.BinhLuan;
import k23cnt3.vutienduc.project3.fast_food_order.repository.BinhLuanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BinhLuanService {

    private final BinhLuanRepository binhLuanRepository;

    // Lấy tất cả
    public List<BinhLuan> findAll() {
        return binhLuanRepository.findAll();
    }

    // Lấy theo ID
    public Optional<BinhLuan> findById(Long id) {
        return binhLuanRepository.findById(id);
    }

    // Lấy bình luận theo món ăn – mặc định sắp xếp mới nhất
    public List<BinhLuan> findByMonAn(Long monAnId) {
        return binhLuanRepository.findByMonAnIdOrderByNgayTaoDesc(monAnId);
    }

    public List<BinhLuan> filterBinhLuan(Long monAnId, Integer rating, String keyword) {

        List<BinhLuan> result;

        // ✅ KHÔNG chọn món → lấy tất cả
        if (monAnId == null) {
            result = binhLuanRepository.findAll();
        } else {
            result = binhLuanRepository.findByMonAnIdOrderByNgayTaoDesc(monAnId);
        }

        // Lọc theo rating
        if (rating != null) {
            result = result.stream()
                    .filter(bl -> bl.getDanhGia() == rating)
                    .toList();
        }

        // Lọc theo keyword (tên người dùng)
        if (keyword != null && !keyword.trim().isEmpty()) {
            String kw = keyword.toLowerCase();
            result = result.stream()
                    .filter(bl ->
                            bl.getNguoiDung() != null &&
                                    bl.getNguoiDung().getTen() != null &&
                                    bl.getNguoiDung().getTen().toLowerCase().contains(kw)
                    )
                    .toList();
        }

        return result;
    }


    // Lưu
    public BinhLuan save(BinhLuan binhLuan) {
        return binhLuanRepository.save(binhLuan);
    }

    // Xoá
    public void deleteById(Long id) {
        binhLuanRepository.deleteById(id);
    }

    // Rating TB tất cả
    public Double getAverageRating() {
        return Optional.ofNullable(binhLuanRepository.getAverageRating()).orElse(0.0);
    }

    // Rating TB theo món
    public Double getAverageRatingByMonAn(Long monAnId) {
        return Optional.ofNullable(binhLuanRepository.getAverageRatingByMonAnId(monAnId)).orElse(0.0);
    }

    // Đếm tất cả
    public long countAll() {
        return binhLuanRepository.count();
    }

    // Đếm theo món
    public long countByMonAn(Long monAnId) {
        return binhLuanRepository.countByMonAnId(monAnId);
    }
}
