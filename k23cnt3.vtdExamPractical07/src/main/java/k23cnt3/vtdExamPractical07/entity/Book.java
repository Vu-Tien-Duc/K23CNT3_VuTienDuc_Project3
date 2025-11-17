package k23cnt3.vtdExamPractical07.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String imgUrl;

    private int qty;

    private double price;

    private int yearRelease;

    private String author;

    private boolean status;

    // FK tới Category
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
