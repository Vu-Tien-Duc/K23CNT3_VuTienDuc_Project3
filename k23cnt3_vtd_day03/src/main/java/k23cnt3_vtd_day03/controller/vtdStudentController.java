package k23cnt3_vtd_day03.controller;

import k23cnt3_vtd_day03.entity.vtdStudent;
import k23cnt3_vtd_day03.service.vtdServiceStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class vtdStudentController {
    @Autowired
    private vtdServiceStudent studentService;
    @GetMapping("/student-list")
    public List<vtdStudent> getAllStudents() {
        return studentService.getStudents();
    }
    @GetMapping("/student/{id}")
    public vtdStudent getAllStudents(@PathVariable String id)
    {
        Long param = Long.parseLong(id);
        return studentService.getStudent(param);
    }
    @PostMapping("/student-add")
    public vtdStudent addStudent(@RequestBody vtdStudent student)
    {
        return studentService.addStudent(student);
    }
    @PutMapping("/student/{id}")
    public vtdStudent updateStudent(@PathVariable String id,
                                 @RequestBody vtdStudent student) {
        Long param = Long.parseLong(id);
        return studentService.updateStudent(param,
                student);
    }
    @DeleteMapping("/student/{id}")
    public boolean deleteStudent(@PathVariable String id) {
        Long param = Long.parseLong(id);
        return studentService.deleteStudent(param);
    }
}
