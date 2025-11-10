package k23cnt3_vtd_lab04.controller;



import k23cnt3_vtd_lab04.entity.VtdKhoa;
import k23cnt3_vtd_lab04.service.VtdKhoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vtdkhoa")
public class VtdKhoaController {

    @Autowired
    private VtdKhoaService khoaService;

    @GetMapping
    public List<VtdKhoa> getAll() {
        return khoaService.getAll();
    }

    @GetMapping("/{makh}")
    public VtdKhoa getById(@PathVariable String makh) {
        return khoaService.getById(makh);
    }

    @PostMapping
    public String add(@RequestBody VtdKhoa khoa) {
        khoaService.add(khoa);
        return "Thêm khoa thành công!";
    }

    @PutMapping("/{makh}")
    public String update(@PathVariable String makh, @RequestBody VtdKhoa khoa) {
        return khoaService.update(makh, khoa) ? "Cập nhật thành công!" : "Không tìm thấy khoa!";
    }

    @DeleteMapping("/{makh}")
    public String delete(@PathVariable String makh) {
        return khoaService.delete(makh) ? "Xóa thành công!" : "Không tìm thấy khoa!";
    }
}