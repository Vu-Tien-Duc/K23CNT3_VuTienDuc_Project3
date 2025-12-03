package k23cnt3.vutienduc.project3.fast_food_order.service;

import k23cnt3.vutienduc.project3.fast_food_order.entity.MonAn;
import k23cnt3.vutienduc.project3.fast_food_order.entity.TheLoai;
import k23cnt3.vutienduc.project3.fast_food_order.repository.MonAnRepository;
import k23cnt3.vutienduc.project3.fast_food_order.repository.TheLoaiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MonAnService {

    private final MonAnRepository monAnRepository;
    private final TheLoaiRepository theLoaiRepository;

    public Page<MonAn> getAll(int page, int size, String search, Long theLoaiId) {
        Pageable pageable = PageRequest.of(page, size);

        if (theLoaiId != null) {
            TheLoai theLoai = theLoaiRepository.findById(theLoaiId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại"));
            return monAnRepository.findByTheLoai(theLoai, pageable);
        } else if (search != null && !search.isEmpty()) {
            return monAnRepository.findByTenContainingIgnoreCase(search, pageable);
        }

        return monAnRepository.findAll(pageable);
    }

    public MonAn getById(Long id) {
        return monAnRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn"));
    }

    public List<MonAn> getByTheLoai(Long theLoaiId) {
        TheLoai theLoai = theLoaiRepository.findById(theLoaiId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại"));
        return monAnRepository.findByTheLoai(theLoai);
    }

    public MonAn create(MonAn monAn) {
        if (monAn.getTheLoai() != null && monAn.getTheLoai().getId() != null) {
            TheLoai theLoai = theLoaiRepository.findById(monAn.getTheLoai().getId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại"));
            monAn.setTheLoai(theLoai);
        }
        return monAnRepository.save(monAn);
    }

    public MonAn update(Long id, MonAn monAn) {
        MonAn existing = getById(id);
        existing.setTen(monAn.getTen());
        existing.setMoTa(monAn.getMoTa());
        existing.setGia(monAn.getGia());
        existing.setHinhAnh(monAn.getHinhAnh());

        if (monAn.getTheLoai() != null && monAn.getTheLoai().getId() != null) {
            TheLoai theLoai = theLoaiRepository.findById(monAn.getTheLoai().getId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại"));
            existing.setTheLoai(theLoai);
        }

        return monAnRepository.save(existing);
    }

    public void delete(Long id) {
        MonAn monAn = monAnRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn"));
        if (!monAn.getChiTietDonHangs().isEmpty()) {
            throw new RuntimeException("Không thể xóa món ăn đã có trong đơn hàng");
        }
        monAnRepository.delete(monAn);
    }

}
