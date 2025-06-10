package com.lamashkevich.tax_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String DEFAULT_MESSAGE = "Internal server error!";

    @ExceptionHandler(TaxRateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleTaxRateNotFoundException(TaxRateNotFoundException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception e) {
        log.error(e.getMessage());
        return DEFAULT_MESSAGE;
    }
}
