package com.lamashkevich.payment_service.client;

import com.lamashkevich.payment_service.dto.BankTransferRequestDto;
import com.lamashkevich.payment_service.dto.BankTransferResponseDto;
import com.lamashkevich.payment_service.entity.PaymentStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"dev", "test"})
@Component
public class FakePaymentProcessor implements PaymentProcessor {
    @Override
    public BankTransferResponseDto pay(BankTransferRequestDto requestDto) {
        return new BankTransferResponseDto(PaymentStatus.COMPLETE);
    }
}
