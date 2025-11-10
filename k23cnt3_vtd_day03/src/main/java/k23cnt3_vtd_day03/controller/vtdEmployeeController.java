package k23cnt3_vtd_day03.controller;

import k23cnt3_vtd_day03.entity.vtdEmployee;
import k23cnt3_vtd_day03.service.vtdEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee") // Đường dẫn API gọn
public class vtdEmployeeController {

    @Autowired
    private vtdEmployeeService employeeService;

    // Lấy toàn bộ nhân viên
    @GetMapping("/list")
    public List<vtdEmployee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // Lấy nhân viên theo id
    @GetMapping("/{id}")
    public vtdEmployee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    // Thêm nhân viên mới
    @PostMapping("/add")
    public vtdEmployee addEmployee(@RequestBody vtdEmployee employee) {
        return employeeService.addEmployee(employee);
    }

    // Cập nhật nhân viên theo id
    @PutMapping("/{id}")
    public vtdEmployee updateEmployee(@PathVariable Long id, @RequestBody vtdEmployee employee) {
        return employeeService.updateEmployee(id, employee);
    }

    // Xóa nhân viên theo id
    @DeleteMapping("/{id}")
    public boolean deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteEmployee(id);
    }
}
