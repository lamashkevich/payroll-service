package com.lamashkevich.tax_service.dto;

import java.math.BigDecimal;

public record TaxRateRequestDto(
        String region,
        String position,
        BigDecimal rate
) {
}
