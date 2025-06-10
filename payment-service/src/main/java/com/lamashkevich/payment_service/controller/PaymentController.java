package com.lamashkevich.payment_service.controller;

import com.lamashkevich.payment_service.dto.PaymentFilter;
import com.lamashkevich.payment_service.dto.PaymentRequestDto;
import com.lamashkevich.payment_service.dto.PaymentResponseDto;
import com.lamashkevich.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentResponseDto createPayment(@RequestBody PaymentRequestDto requestDto) {
        return paymentService.createPayment(requestDto);
    }

    @GetMapping
    public List<PaymentResponseDto> getAll(@ModelAttribute PaymentFilter filter) {
        return paymentService.getAllByFilter(filter);
    }

    @GetMapping("/{id}")
    public PaymentResponseDto getById(@PathVariable Long id) {
        return paymentService.getById(id);
    }
}
