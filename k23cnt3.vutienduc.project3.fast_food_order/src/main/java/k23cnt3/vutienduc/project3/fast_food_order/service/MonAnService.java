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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MonAnService {

    private final MonAnRepository monAnRepository;
    private final TheLoaiRepository theLoaiRepository;

    /**
     * Đếm tổng số món ăn trong database.
     * Dùng cho dashboard thống kê.
     */
    public long countAll() {
        return monAnRepository.count();
    }

    /**
     * Lấy danh sách toàn bộ món ăn (không phân trang).
     * Bạn dùng cho random gợi ý hoặc select box.
     */
    public List<MonAn> findAll() {
        return monAnRepository.findAll();
    }

    /**
     * Hàm chính để lấy danh sách món ăn với các tính năng:
     * - Phân trang
     * - Tìm kiếm theo tên
     * - Lọc theo ID thể loại
     * - Lọc theo tên thể loại
     *
     * Ưu tiên xử lý theo thứ tự:
     * 1) Lọc theo *tên thể loại*
     * 2) Lọc theo *ID thể loại*
     * 3) Tìm *theo tên món ăn*
     * 4) Không có filter → trả về tất cả
     */
    public Page<MonAn> getAll(int page, int size, String search, Long theLoaiId, String tenTheLoai) {
        Pageable pageable = PageRequest.of(page, size);

        // Nếu lọc theo tên thể loại
        if (tenTheLoai != null && !tenTheLoai.isEmpty()) {
            return monAnRepository.findByTheLoai_TenTheLoaiIgnoreCase(tenTheLoai, pageable);
        }

        // Nếu lọc theo ID thể loại
        if (theLoaiId != null) {
            TheLoai theLoai = theLoaiRepository.findById(theLoaiId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại"));
            return monAnRepository.findByTheLoai(theLoai, pageable);
        }

        // Nếu tìm kiếm theo tên món ăn
        if (search != null && !search.isEmpty()) {
            return monAnRepository.findByTenContainingIgnoreCase(search, pageable);
        }

        // Mặc định lấy tất cả
        return monAnRepository.findAll(pageable);
    }

    /**
     * Lấy danh sách món ăn random (ngẫu nhiên).
     * Dùng cho phần “Gợi ý dành cho bạn”.
     *
     * B1: Lấy toàn bộ món ăn
     * B2: Shuffle() để trộn ngẫu nhiên
     * B3: Lấy limit món đầu tiên
     */
    public List<MonAn> getRandomFoods(int limit) {
        List<MonAn> list = monAnRepository.findAll();
        Collections.shuffle(list);
        return list.stream().limit(limit).toList();
    }

    /**
     * Overload — gọi phiên bản đầy đủ 5 tham số nhưng bỏ qua tenTheLoai.
     * Dùng để tương thích với controller cũ của bạn.
     */
    public Page<MonAn> getAll(int page, int size, String search, Long theLoaiId) {
        return getAll(page, size, search, theLoaiId, null);
    }

    /**
     * Lấy món ăn theo ID.
     * Nếu không tìm thấy → báo lỗi.
     */
    public MonAn getById(Long id) {
        return monAnRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn"));
    }

    /**
     * Lấy danh sách món ăn của một thể loại.
     * Dùng cho trang thể loại hoặc gợi ý món cùng thể loại.
     */
    public List<MonAn> getByTheLoai(Long theLoaiId) {
        TheLoai theLoai = theLoaiRepository.findById(theLoaiId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại"));
        return monAnRepository.findByTheLoai(theLoai);
    }

    /**
     * Tạo món ăn mới.
     * - Kiểm tra thể loại có tồn tại không
     * - Lưu vào DB
     */
    public MonAn create(MonAn monAn) {
        if (monAn.getTheLoai() != null && monAn.getTheLoai().getId() != null) {
            TheLoai theLoai = theLoaiRepository.findById(monAn.getTheLoai().getId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại"));
            monAn.setTheLoai(theLoai);
        }
        return monAnRepository.save(monAn);
    }

    /**
     * Cập nhật thông tin món ăn.
     * - Không tạo mới object → dùng object cũ để tránh mất dữ liệu quan hệ.
     * - Copy nội dung từ object mới sang object cũ.
     */
    public MonAn update(Long id, MonAn monAn) {
        MonAn existing = getById(id);  // lấy món cũ

        // Cập nhật các thuộc tính
        existing.setTen(monAn.getTen());
        existing.setMoTa(monAn.getMoTa());
        existing.setGia(monAn.getGia());
        existing.setGiaCu(monAn.getGiaCu());

        // Cập nhật danh sách hình ảnh
        existing.setHinhAnh(
                monAn.getHinhAnh() != null ? new ArrayList<>(monAn.getHinhAnh()) : new ArrayList<>()
        );

        // Cập nhật thể loại
        if (monAn.getTheLoai() != null && monAn.getTheLoai().getId() != null) {
            TheLoai theLoai = theLoaiRepository.findById(monAn.getTheLoai().getId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại"));
            existing.setTheLoai(theLoai);
        }

        return monAnRepository.save(existing);
    }

    /**
     * Xóa món ăn.
     * - Không được xóa nếu món đã tồn tại trong đơn hàng.
     */
    public void delete(Long id) {
        MonAn monAn = monAnRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn"));

        // Nếu món ăn có chi tiết đơn hàng → không cho xóa
        if (!monAn.getChiTietDonHangs().isEmpty()) {
            throw new RuntimeException("Không thể xóa món ăn đã có trong đơn hàng");
        }

        monAnRepository.delete(monAn);
    }
}
