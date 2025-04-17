package com.lamashkevich.payroll_calclulation_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PayrollCalculationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayrollCalculationServiceApplication.class, args);
    }

}
