package com.lamashkevich.payment_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentFilter(
        BigDecimal minAmount,
        BigDecimal maxAmount,
        LocalDate startDate,
        LocalDate endDate
) {
}
