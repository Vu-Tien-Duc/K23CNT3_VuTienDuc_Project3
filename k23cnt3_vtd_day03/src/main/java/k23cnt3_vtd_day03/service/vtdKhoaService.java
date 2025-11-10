package k23cnt3_vtd_day03.service;

import k23cnt3_vtd_day03.entity.vtdKhoa;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class vtdKhoaService {

    private List<vtdKhoa> khoas = new ArrayList<>();

    public vtdKhoaService() {
        khoas.addAll(Arrays.asList(
                new vtdKhoa("CNTT", "Công nghệ thông tin"),
                new vtdKhoa("KT", "Kế toán"),
                new vtdKhoa("QTKD", "Quản trị kinh doanh"),
                new vtdKhoa("NN", "Ngôn ngữ Anh"),
                new vtdKhoa("CK", "Cơ khí")
        ));
    }

    // Lấy toàn bộ danh sách khoa
    public List<vtdKhoa> getAllKhoa() {
        return khoas;
    }

    // Lấy khoa theo id
    public vtdKhoa getKhoaById(String id) {
        return khoas.stream()
                .filter(k -> k.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    // Thêm mới khoa
    public vtdKhoa addKhoa(vtdKhoa khoa) {
        khoas.add(khoa);
        return khoa;
    }

    // Cập nhật thông tin khoa
    public vtdKhoa updateKhoa(String id, vtdKhoa khoa) {
        vtdKhoa existing = getKhoaById(id);
        if (existing != null) {
            existing.setTen(khoa.getTen());
            return existing;
        }
        return null;
    }

    // Xóa khoa
    public boolean deleteKhoa(String id) {
        vtdKhoa existing = getKhoaById(id);
        if (existing != null) {
            return khoas.remove(existing);
        }
        return false;
    }
}
