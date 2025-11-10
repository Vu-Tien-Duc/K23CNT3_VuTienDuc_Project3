package k23cnt3_vtd_lab04.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VtdKhoaDTO {
    @NotBlank(message = "Mã khoa không được để trống")
    @Size(min = 2, message = "Mã khoa phải ít nhất 2 ký tự")
    private String makh;

    @Size(min = 5, max = 25, message = "Tên khoa từ 5–25 ký tự")
    private String tenkh;
}