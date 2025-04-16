package com.lamashkevich.payroll_calclulation_service.dto;

import com.lamashkevich.payroll_calclulation_service.entity.PaymentType;

import java.math.BigDecimal;

public record PayrollResultDTO(
        Long employeeId,
        BigDecimal baseSalary,
        BigDecimal totalBonuses,
        BigDecimal totalDeductions,
        BigDecimal taxAmount,
        BigDecimal finalSalary,
        PaymentType paymentType
) {
}
