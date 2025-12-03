package k23cnt3.vutienduc.project3.fast_food_order.service;

import k23cnt3.vutienduc.project3.fast_food_order.entity.BinhLuan;
import k23cnt3.vutienduc.project3.fast_food_order.entity.MonAn;
import k23cnt3.vutienduc.project3.fast_food_order.entity.NguoiDung;
import k23cnt3.vutienduc.project3.fast_food_order.repository.BinhLuanRepository;
import k23cnt3.vutienduc.project3.fast_food_order.repository.MonAnRepository;
import k23cnt3.vutienduc.project3.fast_food_order.repository.NguoiDungRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BinhLuanService {

    private final BinhLuanRepository binhLuanRepository;
    private final NguoiDungRepository nguoiDungRepository;
    private final MonAnRepository monAnRepository;

    private NguoiDung getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return nguoiDungRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
    }

    public BinhLuan create(BinhLuan binhLuan) {
        if (binhLuan.getNoiDung() == null || binhLuan.getNoiDung().isBlank()) {
            throw new RuntimeException("Nội dung bình luận không được để trống");
        }

        NguoiDung nguoiDung = getCurrentUser();
        MonAn monAn = monAnRepository.findById(binhLuan.getMonAn().getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn"));

        binhLuan.setNguoiDung(nguoiDung);
        binhLuan.setMonAn(monAn);
        binhLuan.setNgayTao(LocalDateTime.now());

        return binhLuanRepository.save(binhLuan);
    }

    public void delete(Long id) {
        BinhLuan binhLuan = binhLuanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bình luận"));

        NguoiDung currentUser = getCurrentUser();
        if (!binhLuan.getNguoiDung().getId().equals(currentUser.getId()) &&
                !currentUser.getVaiTro().getTenVaiTro().equals("ADMIN")) {
            throw new RuntimeException("Không có quyền xóa bình luận này");
        }

        binhLuanRepository.delete(binhLuan);
    }

    public List<BinhLuan> getByMonAn(Long monAnId) {
        MonAn monAn = monAnRepository.findById(monAnId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn"));
        return binhLuanRepository.findByMonAnOrderByNgayTaoDesc(monAn);
    }

    public List<BinhLuan> getMyReviews() {
        return binhLuanRepository.findByNguoiDungOrderByNgayTaoDesc(getCurrentUser());
    }
}
