package com.lamashkevich.payment_service.repository;

import com.lamashkevich.payment_service.entity.Payment;
import com.lamashkevich.payment_service.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByStatusAndPaymentDate(PaymentStatus status, LocalDate paymentDate);

}
