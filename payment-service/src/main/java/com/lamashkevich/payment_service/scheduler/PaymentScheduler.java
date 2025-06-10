package com.lamashkevich.payment_service.scheduler;

import com.lamashkevich.payment_service.client.PaymentProcessor;
import com.lamashkevich.payment_service.dto.BankTransferRequestDto;
import com.lamashkevich.payment_service.entity.Payment;
import com.lamashkevich.payment_service.entity.PaymentStatus;
import com.lamashkevich.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentScheduler {

    private final PaymentRepository paymentRepository;
    private final PaymentProcessor paymentProcessor;

    @Scheduled(
            cron = "${scheduler.payment-process.cron}",
            zone = "${scheduler.payment-process.zone}"
    )
    public void paymentProcessScheduler() {
        log.info("Starting payment process");
        List<Payment> payments = paymentRepository
                .findByStatusAndPaymentDate(PaymentStatus.PENDING, LocalDate.now());

        payments.forEach(payment -> {
            try {
                var response = paymentProcessor.pay(new BankTransferRequestDto(
                        payment.getIBAN(),
                        payment.getAmount()
                ));
                payment.setStatus(response.status());
            } catch (Exception e) {
                log.warn(e.getMessage());
                payment.setStatus(PaymentStatus.FAILED);
            }
        });

        paymentRepository.saveAll(payments);
        log.info("Payment process is completed");
    }
}
