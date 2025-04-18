package com.lamashkevich.payment_service.exception;

import java.time.LocalDate;

public class InvalidPaymentDateException extends RuntimeException {
    public InvalidPaymentDateException(LocalDate date) {
        super("Invalid payment date: " + date);
    }
}
