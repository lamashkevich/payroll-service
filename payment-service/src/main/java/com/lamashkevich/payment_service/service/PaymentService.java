package com.lamashkevich.payment_service.service;

import com.lamashkevich.payment_service.client.EmployeeClient;
import com.lamashkevich.payment_service.dto.PaymentRequestDto;
import com.lamashkevich.payment_service.dto.PaymentResponseDto;
import com.lamashkevich.payment_service.entity.Payment;
import com.lamashkevich.payment_service.entity.PaymentStatus;
import com.lamashkevich.payment_service.exception.InvalidPaymentDateException;
import com.lamashkevich.payment_service.exception.PaymentNotFoundException;
import com.lamashkevich.payment_service.mapper.PaymentMapper;
import com.lamashkevich.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final EmployeeClient employeeClient;

    public PaymentResponseDto createPayment(PaymentRequestDto requestDto) {
        log.info("Creating payment using data {}", requestDto);

        if (!validDate(requestDto.date())) {
            throw new InvalidPaymentDateException(requestDto.date());
        }

        var bankInfo = employeeClient.getBankInfoByEmployeeId(requestDto.employeeId());

        var payment = Payment.builder()
                .amount(requestDto.amount())
                .employeeId(requestDto.employeeId())
                .IBAN(bankInfo.IBAN())
                .paymentDate(requestDto.date())
                .status(PaymentStatus.PENDING)
                .build();

        paymentRepository.save(payment);

        return paymentMapper.paymentToPaymentResponseDto(payment);
    }

    public List<PaymentResponseDto> getAll() {
        log.info("Getting all payments");
        return paymentRepository.findAll().stream()
                .map(paymentMapper::paymentToPaymentResponseDto)
                .toList();
    }

    public PaymentResponseDto getById(Long id) {
        log.info("Getting payment with id: {}", id);
        return paymentRepository.findById(id)
                .map(paymentMapper::paymentToPaymentResponseDto)
                .orElseThrow(() -> new PaymentNotFoundException(id));
    }

    private boolean validDate(LocalDate paymentDate) {
        return paymentDate.isAfter(LocalDate.now());
    }
}
