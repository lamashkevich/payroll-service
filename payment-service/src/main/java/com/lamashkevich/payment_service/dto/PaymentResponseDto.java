package com.lamashkevich.payment_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lamashkevich.payment_service.entity.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PaymentResponseDto(
        Long id,
        Long employeeId,
        BigDecimal amount,
        String IBAN,
        PaymentStatus status,
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate paymentDate,
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime updatedAt
) {
}
