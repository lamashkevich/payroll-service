package com.lamashkevich.payment_service.client;

import com.lamashkevich.payment_service.dto.EmployeeBankInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "employee-service", url = "${client.employee-service.url}")
public interface EmployeeClient {

    @GetMapping("/api/v1/employees/bank/{employeeId}")
    EmployeeBankInfoDto getBankInfoByEmployeeId(@PathVariable Long employeeId);

}
