package com.lamashkevich.payroll_calculation_service.mapper;

import com.lamashkevich.payroll_calculation_service.dto.PayrollResultDTO;
import com.lamashkevich.payroll_calculation_service.entity.Payroll;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PayrollMapper {

    PayrollResultDTO payrollToPayrollResultDto(Payroll payroll);

}
