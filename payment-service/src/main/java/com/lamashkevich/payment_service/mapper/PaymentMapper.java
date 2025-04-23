package com.lamashkevich.payment_service.mapper;

import com.lamashkevich.payment_service.dto.PaymentRequestDto;
import com.lamashkevich.payment_service.dto.PaymentResponseDto;
import com.lamashkevich.payment_service.entity.Payment;
import com.lamashkevich.payment_service.event.PayrollCalculatedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {


    PaymentResponseDto paymentToPaymentResponseDto(Payment payment);

    @Mapping(target = "date", source = "paymentDate")
    PaymentRequestDto eventToPaymentRequestDto(PayrollCalculatedEvent event);

}
