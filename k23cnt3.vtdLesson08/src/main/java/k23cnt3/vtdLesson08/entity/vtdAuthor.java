package k23cnt3.vtdLesson08.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class vtdAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private String name;
    private String description;
    private String imgUrl;
    private String email;
    private String phone;
    private String address;
    private boolean isActive;
    // Tạo mối quan hệ với bảng book
    @ManyToMany(mappedBy = "vtdAuthors")
    private List<vtdBook> books = new ArrayList<>();
}