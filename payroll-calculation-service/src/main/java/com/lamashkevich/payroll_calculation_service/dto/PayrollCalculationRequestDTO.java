package com.lamashkevich.payroll_calculation_service.dto;

import com.lamashkevich.payroll_calculation_service.entity.PaymentType;

import java.math.BigDecimal;
import java.util.List;

public record PayrollCalculationRequestDTO(
        Long employeeId,
        BigDecimal baseSalary,
        List<BigDecimal> bonuses,
        List<BigDecimal> deductions,
        String position,
        String region,
        PaymentType paymentType
) {
}
