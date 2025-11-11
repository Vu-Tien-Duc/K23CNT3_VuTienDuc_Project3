package k23cnt3.vtdLesson06.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VtdCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private String address;
    private String phone;
    private String email;
    private LocalDate birthDay;
    private boolean active;
}
