package com.lamashkevich.payment_service.dto;

import java.math.BigDecimal;

public record BankTransferRequestDto(
        String firstName,
        String lastName,
        String IBAN,
        BigDecimal amount
) {
}
