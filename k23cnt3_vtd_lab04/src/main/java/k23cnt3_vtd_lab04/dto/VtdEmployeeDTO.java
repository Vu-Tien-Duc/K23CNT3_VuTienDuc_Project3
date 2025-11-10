package k23cnt3_vtd_lab04.dto;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VtdEmployeeDTO {
    private int id;

    @Size(min = 3, max = 25, message = "Tên từ 3–25 ký tự")
    private String fullName;

    @Min(value = 18, message = "Tuổi tối thiểu 18")
    @Max(value = 60, message = "Tuổi tối đa 60")
    private int age;

    @Positive(message = "Lương phải > 0")
    private double salary;
}