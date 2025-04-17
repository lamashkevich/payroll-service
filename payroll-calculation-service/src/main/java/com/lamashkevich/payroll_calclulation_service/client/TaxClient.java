package com.lamashkevich.payroll_calclulation_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "tax-service", url = "${client.tax-service.url}")
public interface TaxClient {

    @GetMapping("/api/v1/taxes/rate")
    BigDecimal getTaxRate(@RequestParam String region, @RequestParam String position);

}
