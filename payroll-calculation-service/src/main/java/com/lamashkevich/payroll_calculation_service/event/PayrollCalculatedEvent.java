package com.lamashkevich.payroll_calculation_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollCalculatedEvent {
    private Long employeeId;
    private BigDecimal amount;
    private LocalDate paymentDate;
}
