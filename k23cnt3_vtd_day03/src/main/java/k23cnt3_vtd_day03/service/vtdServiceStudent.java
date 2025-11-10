package k23cnt3_vtd_day03.service;

import k23cnt3_vtd_day03.entity.vtdStudent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class vtdServiceStudent {

    private List<vtdStudent> students = new ArrayList<>();

    public vtdServiceStudent() {
        students.addAll(Arrays.asList(
                new vtdStudent(1L, "Vũ Tiến Đức", 20, "Nam", "Nguyễn Trãi", "03967025566", "vutienduc@gmail.com"),
                new vtdStudent(2L, "Devmaster 2", 25, "Nữ", "Số 25 VNP", "0978611889", "contact@devmaster.edu.vn"),
                new vtdStudent(3L, "Devmaster 3", 22, "Nam", "Số 25 VNP", "0978611889", "hocvien@devmaster.vn")
        ));
    }

    // Lấy toàn bộ danh sách sinh viên
    public List<vtdStudent> getStudents() {
        return students;
    }

    // Lấy sinh viên theo id
    public vtdStudent getStudent(Long id) {
        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Thêm mới một sinh viên
    public vtdStudent addStudent(vtdStudent student) {
        students.add(student);
        return student;
    }

    // Cập nhật thông tin sinh viên
    public vtdStudent updateStudent(Long id, vtdStudent student) {
        vtdStudent existing = getStudent(id);
        if (existing == null) {
            return null;
        }

        existing.setName(student.getName());
        existing.setAge(student.getAge());
        existing.setGender(student.getGender());
        existing.setAddress(student.getAddress());
        existing.setPhone(student.getPhone());
        existing.setEmail(student.getEmail());

        return existing;
    }

    // Xóa thông tin sinh viên
    public boolean deleteStudent(Long id) {
        vtdStudent student = getStudent(id);
        if (student != null) {
            return students.remove(student);
        }
        return false;
    }
}
