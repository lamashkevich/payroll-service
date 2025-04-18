package com.lamashkevich.payment_service.dto;

public record EmployeeBankInfoDto(
        String firstName,
        String lastName,
        String IBAN
) {
}
