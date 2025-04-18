package com.lamashkevich.payment_service.mapper;

import com.lamashkevich.payment_service.dto.PaymentResponseDto;
import com.lamashkevich.payment_service.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {


    PaymentResponseDto paymentToPaymentResponseDto(Payment payment);

}
