package com.lamashkevich.payment_service.dto;

import com.lamashkevich.payment_service.entity.PaymentStatus;

public record BankTransferResponseDto(
        PaymentStatus status
) {
}
