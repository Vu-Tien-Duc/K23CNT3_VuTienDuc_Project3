package k23cnt3_vtd_lab04.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VtdEmployee {
    private int id;
    private String fullName;
    private String gender;
    private int age;
    private double salary;
}