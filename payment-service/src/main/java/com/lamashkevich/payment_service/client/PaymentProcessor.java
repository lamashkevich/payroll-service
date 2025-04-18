package com.lamashkevich.payment_service.client;

import com.lamashkevich.payment_service.dto.BankTransferRequestDto;
import com.lamashkevich.payment_service.dto.BankTransferResponseDto;

public interface PaymentProcessor {
    BankTransferResponseDto pay(BankTransferRequestDto requestDto);
}
