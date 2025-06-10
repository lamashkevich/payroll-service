package com.lamashkevich.payment_service.dto;

import java.math.BigDecimal;

public record BankTransferRequestDto(
        String IBAN,
        BigDecimal amount
) {
}
