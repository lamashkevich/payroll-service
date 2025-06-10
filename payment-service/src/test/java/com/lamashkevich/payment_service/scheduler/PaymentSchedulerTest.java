package com.lamashkevich.payment_service.scheduler;

import com.lamashkevich.payment_service.client.PaymentProcessor;
import com.lamashkevich.payment_service.dto.BankTransferResponseDto;
import com.lamashkevich.payment_service.entity.Payment;
import com.lamashkevich.payment_service.entity.PaymentStatus;
import com.lamashkevich.payment_service.repository.PaymentRepository;
import com.lamashkevich.payment_service.utils.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PaymentSchedulerTest extends BaseIntegrationTest {

    private static final String VALID_IBAN = "GB33BUKB20201555555555";

    @Autowired
    private PaymentScheduler paymentScheduler;

    @Autowired
    private PaymentRepository paymentRepository;

    @MockitoBean
    private PaymentProcessor paymentProcessor;

    @Test
    void processForDate() {
        var payment = Payment.builder()
                .amount(BigDecimal.valueOf(1000))
                .IBAN(VALID_IBAN)
                .status(PaymentStatus.PENDING)
                .paymentDate(LocalDate.now())
                .build();

        var savedPayments = paymentRepository.saveAll(List.of(payment, payment));

        when(paymentProcessor.pay(any())).thenReturn(new BankTransferResponseDto(PaymentStatus.COMPLETE));

        paymentScheduler.paymentProcessScheduler();

        savedPayments.forEach(i -> assertEquals(PaymentStatus.COMPLETE, i.getStatus()));
    }
}