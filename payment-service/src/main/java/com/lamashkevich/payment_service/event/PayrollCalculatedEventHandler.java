package com.lamashkevich.payment_service.event;

import com.lamashkevich.payment_service.mapper.PaymentMapper;
import com.lamashkevich.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = "${kafka.topics.payroll-calculated}")
public class PayrollCalculatedEventHandler {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    @KafkaHandler
    public void handle(PayrollCalculatedEvent event) {
        log.info("Received payroll event: {}", event);
        var paymentRequest = paymentMapper.eventToPaymentRequestDto(event);
        paymentService.createPayment(paymentRequest);
    }

}
