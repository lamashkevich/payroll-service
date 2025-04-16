package com.lamashkevich.payroll_calclulation_service.controller;

import com.lamashkevich.payroll_calclulation_service.dto.PayrollCalculationRequestDTO;
import com.lamashkevich.payroll_calclulation_service.dto.PayrollResultDTO;
import com.lamashkevich.payroll_calclulation_service.service.PayrollCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payrolls")
public class PayrollCalculationController {

    private final PayrollCalculationService calculationService;

    @PostMapping
    public PayrollResultDTO calculate(@RequestBody PayrollCalculationRequestDTO requestDTO) {
        return calculationService.calculate(requestDTO);
    }
}
