package com.lamashkevich.payment_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentRequestDto(
        Long employeeId,
        BigDecimal amount,
        LocalDate date
) {
}
