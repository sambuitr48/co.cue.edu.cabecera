package mapping.dto;

import java.util.Date;

public record EmployeeDTO(int employee_id,
                          String employee_name,
                          Date employee_age) {
}
