package k23cnt3.vtd.project3.movie.service;

import k23cnt3.vtd.project3.movie.entity.Phim;
import k23cnt3.vtd.project3.movie.entity.TheLoai;
import k23cnt3.vtd.project3.movie.repository.PhimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PhimService {

    private final PhimRepository phimRepository;

    // Thêm hoặc cập nhật phim
    public Phim save(Phim phim) {
        if (phim.getNgayTao() == null) {
            phim.setNgayTao(LocalDateTime.now());
        }
        return phimRepository.save(phim);
    }

    // Xóa phim
    public void delete(Long id) {
        phimRepository.deleteById(id);
    }

    // Tìm phim theo ID
    public Optional<Phim> findById(Long id) {
        return phimRepository.findById(id);
    }

    // Thêm thể loại cho phim
    public Phim addTheLoai(Phim phim, TheLoai theLoai) {
        Set<TheLoai> set = phim.getTheLoais();
        if (set == null) set = new HashSet<>();
        set.add(theLoai);
        phim.setTheLoais(set);
        return phimRepository.save(phim);
    }

    // Xóa thể loại khỏi phim
    public Phim removeTheLoai(Phim phim, TheLoai theLoai) {
        Set<TheLoai> set = phim.getTheLoais();
        if (set != null) {
            set.remove(theLoai);
            phim.setTheLoais(set);
        }
        return phimRepository.save(phim);
    }

    // Lọc phim nâng cao kèm phân trang
    public Page<Phim> searchPhim(String keyword, List<TheLoai> theLoais,
                                 Integer namPhatHanh, Double minRating,
                                 Pageable pageable) {

        Page<Phim> page = phimRepository.findAll(pageable);

        // Lọc theo keyword
        if (keyword != null && !keyword.isEmpty()) {
            page = phimRepository.findByKeyword(keyword, pageable);
        }

        // Lọc theo thể loại
        if (theLoais != null && !theLoais.isEmpty()) {
            page = phimRepository.findByTheLoais(theLoais, pageable);
        }

        // Lọc theo năm phát hành
        if (namPhatHanh != null) {
            page = phimRepository.findByNamPhatHanh(namPhatHanh, pageable);
        }

        // Lọc theo đánh giá trung bình
        if (minRating != null) {
            page = phimRepository.findByMinAverageRating(minRating, pageable);
        }

        return page;
    }

    // Lấy tất cả phim
    public List<Phim> findAll() {
        return phimRepository.findAll();
    }
}
