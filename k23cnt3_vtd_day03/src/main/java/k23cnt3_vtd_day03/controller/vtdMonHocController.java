package k23cnt3_vtd_day03.controller;

import k23cnt3_vtd_day03.entity.vtdMonHoc;
import k23cnt3_vtd_day03.service.vtdMonHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monhoc") // Đường dẫn API gọn
public class vtdMonHocController {

    @Autowired
    private vtdMonHocService monHocService;

    // Lấy tất cả môn học
    @GetMapping("/list")
    public List<vtdMonHoc> getAllMonHoc() {
        return monHocService.getAllMonHoc();
    }

    // Lấy môn học theo id
    @GetMapping("/{id}")
    public vtdMonHoc getMonHocById(@PathVariable String id) {
        return monHocService.getMonHocById(id);
    }

    // Thêm môn học mới
    @PostMapping("/add")
    public vtdMonHoc addMonHoc(@RequestBody vtdMonHoc monHoc) {
        return monHocService.addMonHoc(monHoc);
    }

    // Cập nhật môn học theo id
    @PutMapping("/{id}")
    public vtdMonHoc updateMonHoc(@PathVariable String id, @RequestBody vtdMonHoc monHoc) {
        return monHocService.updateMonHoc(id, monHoc);
    }

    // Xóa môn học theo id
    @DeleteMapping("/{id}")
    public boolean deleteMonHoc(@PathVariable String id) {
        return monHocService.deleteMonHoc(id);
    }
}
