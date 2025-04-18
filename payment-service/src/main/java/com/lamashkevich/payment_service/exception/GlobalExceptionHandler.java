package com.lamashkevich.payment_service.exception;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String DEFAULT_MESSAGE = "Internal server error!";

    @ExceptionHandler(InvalidPaymentDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidPaymentDateException(InvalidPaymentDateException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlePaymentNotFoundException(PaymentNotFoundException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleFeignException(FeignException e) {
        log.error(e.getMessage());
        return parseFeignMessage(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception e) {
        log.error(e.getMessage());
        return DEFAULT_MESSAGE;
    }

    private String parseFeignMessage(String content) {
        log.info(content);
        int msgStart = content.lastIndexOf("[") + 1;
        int msgEnd = content.lastIndexOf("]");
        return content.substring(msgStart, msgEnd);
    }
}
