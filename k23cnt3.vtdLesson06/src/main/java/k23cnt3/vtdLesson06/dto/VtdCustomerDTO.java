package k23cnt3.vtdLesson06.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VtdCustomerDTO {

    private Long id;

    @NotBlank(message = "Username không được để trống")
    private String username;

    @Size(min = 6, message = "Password tối thiểu 6 ký tự")
    private String password;

    @NotBlank(message = "Full Name không được để trống")
    private String fullName;

    private String address;

    @Pattern(regexp = "^(0|\\+84)[0-9]{9,10}$", message = "Phone không hợp lệ")
    private String phone;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    private String email;

    @Past(message = "Ngày sinh phải là quá khứ")
    private LocalDate birthDay;

    private boolean active;
}
