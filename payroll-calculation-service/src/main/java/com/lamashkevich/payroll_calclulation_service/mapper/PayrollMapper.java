package com.lamashkevich.payroll_calclulation_service.mapper;

import com.lamashkevich.payroll_calclulation_service.dto.PayrollResultDTO;
import com.lamashkevich.payroll_calclulation_service.entity.Payroll;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PayrollMapper {

    PayrollResultDTO payrollToPayrollResultDto(Payroll payroll);

}
