package com.lamashkevich.tax_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TaxRateResponseDto(
        String region,
        String position,
        BigDecimal totalRate,
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
        LocalDateTime updatedAt) {
}
