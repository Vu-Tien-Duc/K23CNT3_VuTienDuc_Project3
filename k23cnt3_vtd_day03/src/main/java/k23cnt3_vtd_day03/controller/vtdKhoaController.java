package k23cnt3_vtd_day03.controller;

import k23cnt3_vtd_day03.entity.vtdKhoa;
import k23cnt3_vtd_day03.service.vtdKhoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/khoa") // Đường dẫn API gọn
public class vtdKhoaController {

    @Autowired
    private vtdKhoaService khoaService;

    // Lấy tất cả khoa
    @GetMapping("/list")
    public List<vtdKhoa> getAllKhoa() {
        return khoaService.getAllKhoa();
    }

    // Lấy khoa theo id
    @GetMapping("/{id}")
    public vtdKhoa getKhoaById(@PathVariable String id) {
        return khoaService.getKhoaById(id);
    }

    // Thêm mới khoa
    @PostMapping("/add")
    public vtdKhoa addKhoa(@RequestBody vtdKhoa khoa) {
        return khoaService.addKhoa(khoa);
    }

    // Cập nhật khoa theo id
    @PutMapping("/{id}")
    public vtdKhoa updateKhoa(@PathVariable String id, @RequestBody vtdKhoa khoa) {
        return khoaService.updateKhoa(id, khoa);
    }

    // Xóa khoa theo id
    @DeleteMapping("/{id}")
    public boolean deleteKhoa(@PathVariable String id) {
        return khoaService.deleteKhoa(id);
    }
}
