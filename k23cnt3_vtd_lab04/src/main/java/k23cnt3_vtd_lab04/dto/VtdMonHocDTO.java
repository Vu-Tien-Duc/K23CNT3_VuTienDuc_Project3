package k23cnt3_vtd_lab04.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VtdMonHocDTO {
    @Size(min = 2, max = 2, message = "Mã môn học phải gồm 2 ký tự")
    private String mamh;

    @Size(min = 5, max = 50, message = "Tên môn học từ 5–50 ký tự")
    private String tenmh;

    @Size(min = 45, max = 75, message = "Số tiết trong khoảng 45–75")
    private String sotiet;
}