package k23cnt3.vtd.project3.movie.service;

import k23cnt3.vtd.project3.movie.entity.BinhLuan;
import k23cnt3.vtd.project3.movie.entity.Phim;
import k23cnt3.vtd.project3.movie.repository.BinhLuanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BinhLuanService {

    private final BinhLuanRepository binhLuanRepository;

    // Thêm bình luận + đánh giá
    public BinhLuan addBinhLuan(BinhLuan binhLuan) {
        binhLuan.setNgayTao(LocalDateTime.now());
        return binhLuanRepository.save(binhLuan);
    }

    // Lấy tất cả bình luận của 1 phim, sắp xếp mới nhất
    public List<BinhLuan> getBinhLuanByPhim(Phim phim) {
        return binhLuanRepository.findByPhimOrderByNgayTaoDesc(phim);
    }

    // Lấy trung bình đánh giá của 1 phim
    public Double getAverageRatingByPhim(Long phimId) {
        Double avg = binhLuanRepository.findAverageRatingByPhimId(phimId);
        return avg != null ? avg : 0.0;
    }

    public void delete(Long id) {
        binhLuanRepository.deleteById(id);
    }

    public List<BinhLuan> findAll() {
        return binhLuanRepository.findAll();
    }

}
