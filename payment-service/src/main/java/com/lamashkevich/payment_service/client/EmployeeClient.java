package com.lamashkevich.payment_service.client;

import com.lamashkevich.payment_service.dto.EmployeeBankInfoDto;
import com.lamashkevich.security.InternalFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "employee-service",
        url = "${client.internal.url}",
        configuration = InternalFeignConfig.class
)
public interface EmployeeClient {

    @GetMapping("/api/v1/employees/bank/{employeeId}")
    EmployeeBankInfoDto getBankInfoByEmployeeId(@PathVariable Long employeeId);

}
