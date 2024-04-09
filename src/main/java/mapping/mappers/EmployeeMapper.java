package mapping.mappers;

import mapping.dto.EmployeeDTO;
import models.Employee;

public class EmployeeMapper {
    public static EmployeeDTO mapFromModel(Employee employee){
        return new EmployeeDTO(employee.getEmployee_id(), employee.getEmployee_name(), employee.getEmployee_age());
    }
    public static Employee mapFromDto(EmployeeDTO employee) {
        return Employee.builder()
                .employee_id(employee.employee_id())
                .employee_name(employee.employee_name())
                .employee_age(employee.employee_age())
                .build();
    }
}
