package k23cnt3_vtd_day03.service;

import k23cnt3_vtd_day03.entity.vtdEmployee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service quản lý danh sách nhân viên
 * @author Anh Tuấn
 * @version 1.0
 * Date 10/11/2025
 */
@Service
public class vtdEmployeeService {

    private List<vtdEmployee> employees = new ArrayList<>();

    public vtdEmployeeService() {
        employees.addAll(Arrays.asList(
                new vtdEmployee(1L, "Nguyễn Văn A", "Nam", 30, 15000000),
                new vtdEmployee(2L, "Trần Thị B", "Nữ", 25, 12000000),
                new vtdEmployee(3L, "Lê Văn C", "Nam", 42, 25000000),
                new vtdEmployee(4L, "Phạm Thị D", "Nữ", 28, 18000000),
                new vtdEmployee(5L, "Hoàng Văn E", "Nam", 35, 20000000)
        ));
    }

    // Lấy toàn bộ nhân viên
    public List<vtdEmployee> getAllEmployees() {
        return employees;
    }

    // Lấy nhân viên theo id
    public vtdEmployee getEmployeeById(Long id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Thêm nhân viên mới
    public vtdEmployee addEmployee(vtdEmployee employee) {
        employees.add(employee);
        return employee;
    }

    // Cập nhật thông tin nhân viên
    public vtdEmployee updateEmployee(Long id, vtdEmployee employee) {
        vtdEmployee existing = getEmployeeById(id);
        if (existing != null) {
            existing.setFullName(employee.getFullName());
            existing.setGender(employee.getGender());
            existing.setAge(employee.getAge());
            existing.setSalary(employee.getSalary());
            return existing;
        }
        return null;
    }

    // Xóa nhân viên
    public boolean deleteEmployee(Long id) {
        vtdEmployee existing = getEmployeeById(id);
        if (existing != null) {
            return employees.remove(existing);
        }
        return false;
    }
}
