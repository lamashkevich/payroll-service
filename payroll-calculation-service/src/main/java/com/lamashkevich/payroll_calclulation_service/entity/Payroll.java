package com.lamashkevich.payroll_calclulation_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payrolls")
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;

    private BigDecimal baseSalary;

    private BigDecimal totalBonuses;

    private BigDecimal totalDeductions;

    private BigDecimal taxAmount;

    private BigDecimal finalSalary;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
