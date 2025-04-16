package com.lamashkevich.payroll_calclulation_service.service;

import com.lamashkevich.payroll_calclulation_service.client.TaxClient;
import com.lamashkevich.payroll_calclulation_service.dto.PayrollCalculationRequestDTO;
import com.lamashkevich.payroll_calclulation_service.entity.PaymentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PayrollCalculationServiceTest {

    @Container
    public static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17.4");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private PayrollCalculationService payrollCalculationService;

    @MockitoBean
    private TaxClient taxClient;

    @Test
    void calculate() {
        when(taxClient.getTaxRate(anyString(), anyString())).thenReturn(BigDecimal.valueOf(0.10));

        var request = new PayrollCalculationRequestDTO(
                1L,
                BigDecimal.valueOf(100.25),
                List.of(BigDecimal.valueOf(10.5), BigDecimal.valueOf(5.25)),
                List.of(BigDecimal.valueOf(10)),
                "Manager",
                "Minsk",
                PaymentType.MONTHLY
        );

        var result = payrollCalculationService.calculate(request);

        assertNotNull(result);
        assertEquals(request.paymentType(), result.paymentType());
        assertEquals(request.employeeId(), result.employeeId());
        assertEquals(request.baseSalary(), result.baseSalary());
        assertEquals(0, BigDecimal.valueOf(15.75).compareTo(result.totalBonuses()));
        assertEquals(0, BigDecimal.valueOf(10.00).compareTo(result.totalDeductions()));
        assertEquals(0, BigDecimal.valueOf(10.60).compareTo(result.taxAmount()));
        assertEquals(0, BigDecimal.valueOf(95.40).compareTo(result.finalSalary()));
    }
}