package k23cnt3.vutienduc.project3.fast_food_order.service;

import k23cnt3.vutienduc.project3.fast_food_order.entity.TheLoai;
import k23cnt3.vutienduc.project3.fast_food_order.repository.TheLoaiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TheLoaiService {

    private final TheLoaiRepository theLoaiRepository;

    public long countAll() {
        return theLoaiRepository.count();
    }
    public TheLoai getByTenTheLoai(String ten) {
        return theLoaiRepository.findByTenTheLoai(ten).orElse(null);
    }

    public List<TheLoai> getAll() {
        return theLoaiRepository.findAll();
    }

    public TheLoai getById(Long id) {
        return theLoaiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại"));
    }

    public TheLoai create(TheLoai theLoai) {
        return theLoaiRepository.save(theLoai);
    }

    public TheLoai update(Long id, TheLoai theLoai) {
        TheLoai existing = getById(id);
        existing.setTenTheLoai(theLoai.getTenTheLoai());
        return theLoaiRepository.save(existing);
    }

    public void delete(Long id) {
        TheLoai theLoai = getById(id);

        if (!theLoai.getMonAns().isEmpty()) {
            throw new RuntimeException("Không thể xóa thể loại còn món ăn");
        }

        theLoaiRepository.delete(theLoai);
    }
}
