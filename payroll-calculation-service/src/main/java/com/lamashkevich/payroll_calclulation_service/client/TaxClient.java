package com.lamashkevich.payroll_calclulation_service.client;

import java.math.BigDecimal;

public interface TaxClient {
    BigDecimal getTaxRate(String region, String position);
}
