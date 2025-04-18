package com.lamashkevich.employee_service.dto;

public record EmployeeBankInfoDto(
        String firstName,
        String lastName,
        String IBAN
) {
}
