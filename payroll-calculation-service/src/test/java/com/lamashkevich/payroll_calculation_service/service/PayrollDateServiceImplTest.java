package com.lamashkevich.payroll_calculation_service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PayrollDateServiceImplTest {

    @InjectMocks
    private PayrollDateServiceImpl payrollDateService;

    @Test
    void getPaymentDate() {
        assertEquals(LocalDate.parse("2025-04-15"),
                payrollDateService.getPaymentDate(LocalDate.parse("2025-04-10")));

        assertEquals(LocalDate.parse("2025-04-30"),
                payrollDateService.getPaymentDate(LocalDate.parse("2025-04-15")));

        assertEquals(LocalDate.parse("2025-04-30"),
                payrollDateService.getPaymentDate(LocalDate.parse("2025-04-20")));

        assertEquals(LocalDate.parse("2025-05-15"),
                payrollDateService.getPaymentDate(LocalDate.parse("2025-04-30")));

    }

}