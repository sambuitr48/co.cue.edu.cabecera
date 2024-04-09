package models;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Employee {
    private int employee_id;
    private String employee_name;
    private Date employee_age; //Revisar
}
