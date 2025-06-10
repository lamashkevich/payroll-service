package com.lamashkevich.employee_service.mapper;

import com.lamashkevich.employee_service.dto.EmployeeCreateAndUpdateDto;
import com.lamashkevich.employee_service.dto.EmployeeDto;
import com.lamashkevich.employee_service.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto employeeToEmployeeDto(Employee employee);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Employee employeeCreateDtoToEmployee(EmployeeCreateAndUpdateDto employeeDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void employeeCreateDtoToEmployee(@MappingTarget Employee employee,
                                         EmployeeCreateAndUpdateDto employeeDto);

}
