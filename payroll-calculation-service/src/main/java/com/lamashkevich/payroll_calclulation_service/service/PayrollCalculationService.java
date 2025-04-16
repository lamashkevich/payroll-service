package com.lamashkevich.payroll_calclulation_service.service;

import com.lamashkevich.payroll_calclulation_service.client.TaxClient;
import com.lamashkevich.payroll_calclulation_service.dto.PayrollCalculationRequestDTO;
import com.lamashkevich.payroll_calclulation_service.dto.PayrollResultDTO;
import com.lamashkevich.payroll_calclulation_service.entity.Payroll;
import com.lamashkevich.payroll_calclulation_service.mapper.PayrollMapper;
import com.lamashkevich.payroll_calclulation_service.repository.PayrollRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayrollCalculationService {

    private final PayrollRepository payrollRepository;
    private final PayrollMapper payrollMapper;
    private final TaxClient taxClient;

    public PayrollResultDTO calculate(PayrollCalculationRequestDTO requestDTO) {
        log.info("Calculating for: {}", requestDTO);

        BigDecimal totalBonuses = sum(requestDTO.bonuses());
        BigDecimal totalDeductions = sum(requestDTO.deductions());

        BigDecimal amount = requestDTO.baseSalary()
                .add(totalBonuses)
                .subtract(totalDeductions);

        BigDecimal taxRate = taxClient.getTaxRate(requestDTO.region(), requestDTO.position());
        BigDecimal taxAmount = amount.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);

        BigDecimal finalSalary = amount.subtract(taxAmount);

        Payroll payroll = Payroll.builder()
                .employeeId(requestDTO.employeeId())
                .baseSalary(requestDTO.baseSalary())
                .totalBonuses(totalBonuses)
                .totalDeductions(totalDeductions)
                .taxAmount(taxAmount)
                .finalSalary(finalSalary)
                .paymentType(requestDTO.paymentType())
                .build();

        payrollRepository.save(payroll);

        return payrollMapper.payrollToPayrollResultDto(payroll);
    }

    private BigDecimal sum(List<BigDecimal> list) {
        if (list == null || list.isEmpty()) return BigDecimal.ZERO;
        return list.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
