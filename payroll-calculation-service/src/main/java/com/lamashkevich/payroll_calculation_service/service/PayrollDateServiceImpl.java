package com.lamashkevich.payroll_calculation_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PayrollDateServiceImpl implements PayrollDateService {

    @Override
    public LocalDate getPaymentDate() {
        return getPaymentDate(LocalDate.now());
    }

    public LocalDate getPaymentDate(LocalDate date) {
        if (date.getDayOfMonth() < 15) {
            return date.withDayOfMonth(15);
        } else if (date.getDayOfMonth() == date.lengthOfMonth()) {
            return date.plusMonths(1).withDayOfMonth(15);
        } else {
            return date.withDayOfMonth(date.lengthOfMonth());
        }
    }

}
