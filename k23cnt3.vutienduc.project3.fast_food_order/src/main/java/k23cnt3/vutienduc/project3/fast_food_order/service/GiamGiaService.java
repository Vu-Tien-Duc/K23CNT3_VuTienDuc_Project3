package k23cnt3.vutienduc.project3.fast_food_order.service;

import k23cnt3.vutienduc.project3.fast_food_order.entity.GiamGia;
import k23cnt3.vutienduc.project3.fast_food_order.repository.GiamGiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GiamGiaService {

    private final GiamGiaRepository giamGiaRepository;

    public long countAll() {
        return giamGiaRepository.count();
    }

    public List<GiamGia> getAll() {
        return giamGiaRepository.findAll();
    }

    public GiamGia validate(String maGiamGia) {
        GiamGia giamGia = giamGiaRepository.findByMaGiamGia(maGiamGia)
                .orElseThrow(() -> new RuntimeException("Mã giảm giá không tồn tại"));

        LocalDateTime now = LocalDateTime.now();
        if (giamGia.getNgayBatDau() != null && now.isBefore(giamGia.getNgayBatDau())) {
            throw new RuntimeException("Mã giảm giá chưa có hiệu lực");
        }
        if (giamGia.getNgayKetThuc() != null && now.isAfter(giamGia.getNgayKetThuc())) {
            throw new RuntimeException("Mã giảm giá đã hết hạn");
        }

        return giamGia;
    }

    public GiamGia create(GiamGia giamGia) {
        return giamGiaRepository.save(giamGia);
    }

    public GiamGia update(Long id, GiamGia giamGia) {
        GiamGia existing = giamGiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mã giảm giá"));
        existing.setMaGiamGia(giamGia.getMaGiamGia());
        existing.setGiaTri(giamGia.getGiaTri());
        existing.setLaPhanTram(giamGia.isLaPhanTram());
        existing.setNgayBatDau(giamGia.getNgayBatDau());
        existing.setNgayKetThuc(giamGia.getNgayKetThuc());
        return giamGiaRepository.save(existing);
    }

    public void delete(Long id) {
        giamGiaRepository.deleteById(id);
    }
}