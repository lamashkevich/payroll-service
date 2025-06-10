package com.lamashkevich.employee_service.dto;

import com.lamashkevich.employee_service.entity.EmploymentType;

import java.math.BigDecimal;

public record EmployeeCreateAndUpdateDto(
        String firstName,
        String lastName,
        String email,
        String position,
        String region,
        BigDecimal salary,
        String IBAN,
        EmploymentType employmentType
) {
}
