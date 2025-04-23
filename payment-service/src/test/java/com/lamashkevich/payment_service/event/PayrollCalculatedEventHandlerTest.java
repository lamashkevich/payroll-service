package com.lamashkevich.payment_service.event;

import com.lamashkevich.payment_service.dto.PaymentRequestDto;
import com.lamashkevich.payment_service.mapper.PaymentMapper;
import com.lamashkevich.payment_service.service.PaymentService;
import com.lamashkevich.payment_service.utils.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PayrollCalculatedEventHandlerTest extends BaseIntegrationTest {

    @MockitoSpyBean
    private PayrollCalculatedEventHandler eventHandler;

    @MockitoBean
    private PaymentService paymentService;

    @MockitoBean
    private PaymentMapper paymentMapper;

    @Autowired
    private KafkaTemplate<String, PayrollCalculatedEvent> kafkaTemplate;

    @Value("${kafka.topics.payroll-calculated}")
    private String payrollCalculatedTopic;

    @Test
    void handle() throws ExecutionException, InterruptedException, TimeoutException {
        var event = new PayrollCalculatedEvent(1L, BigDecimal.ONE, LocalDate.now());
        var expectedRequest = new PaymentRequestDto(1L, BigDecimal.ONE, LocalDate.now());

        when(paymentMapper.eventToPaymentRequestDto(any(PayrollCalculatedEvent.class)))
                .thenReturn(expectedRequest);
        when(paymentService.createPayment(expectedRequest)).thenReturn(null);

        Thread.sleep(2000);
        kafkaTemplate.send(payrollCalculatedTopic, event).get(5, SECONDS);

        verify(paymentService, timeout(5000).times(1)).createPayment(expectedRequest);
        verify(eventHandler, timeout(5000).times(1)).handle(any());
    }
}