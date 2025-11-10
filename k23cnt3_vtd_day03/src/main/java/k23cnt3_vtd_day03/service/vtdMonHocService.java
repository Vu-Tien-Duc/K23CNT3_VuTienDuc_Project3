package k23cnt3_vtd_day03.service;

import k23cnt3_vtd_day03.entity.vtdMonHoc;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service quản lý danh sách môn học
 * @author Anh Tuấn
 * @version 1.0
 * Date 10/11/2025
 */
@Service
public class vtdMonHocService {

    private List<vtdMonHoc> monHocs = new ArrayList<>();

    public vtdMonHocService() {
        monHocs.addAll(Arrays.asList(
                new vtdMonHoc("OOP", "Lập trình hướng đối tượng", 45),
                new vtdMonHoc("DB", "Cơ sở dữ liệu", 45),
                new vtdMonHoc("WEB", "Phát triển ứng dụng Web", 60),
                new vtdMonHoc("PRJ", "Dự án mẫu", 30),
                new vtdMonHoc("ENG", "Tiếng Anh chuyên ngành", 30)
        ));
    }

    // Lấy toàn bộ danh sách môn học
    public List<vtdMonHoc> getAllMonHoc() {
        return monHocs;
    }

    // Lấy môn học theo mã môn
    public vtdMonHoc getMonHocById(String id) {
        return monHocs.stream()
                .filter(m -> m.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    // Thêm mới môn học
    public vtdMonHoc addMonHoc(vtdMonHoc monHoc) {
        monHocs.add(monHoc);
        return monHoc;
    }

    // Cập nhật thông tin môn học
    public vtdMonHoc updateMonHoc(String id, vtdMonHoc monHoc) {
        vtdMonHoc existing = getMonHocById(id);
        if (existing != null) {
            existing.setTen(monHoc.getTen());
            existing.setSoTiet(monHoc.getSoTiet());
            return existing;
        }
        return null;
    }

    // Xóa môn học theo mã môn
    public boolean deleteMonHoc(String id) {
        vtdMonHoc existing = getMonHocById(id);
        if (existing != null) {
            return monHocs.remove(existing);
        }
        return false;
    }
}
