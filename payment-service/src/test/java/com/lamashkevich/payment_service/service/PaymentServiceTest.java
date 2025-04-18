package com.lamashkevich.payment_service.service;

import com.lamashkevich.payment_service.client.EmployeeClient;
import com.lamashkevich.payment_service.dto.EmployeeBankInfoDto;
import com.lamashkevich.payment_service.dto.PaymentRequestDto;
import com.lamashkevich.payment_service.entity.PaymentStatus;
import com.lamashkevich.payment_service.exception.InvalidPaymentDateException;
import com.lamashkevich.payment_service.exception.PaymentNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@Sql("/sql/init.sql")
@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PaymentServiceTest {

    @Container
    public static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17.4");

    private static final String VALID_IBAN = "GB33BUKB20201555555555";
    private static final long EXISTING_ID = 1L;
    private static final long NOT_EXISTING_ID = 5L;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @MockitoBean
    private EmployeeClient employeeClient;

    @Autowired
    private PaymentService paymentService;


    @Test
    void createPayment() {
        var validRequest = new PaymentRequestDto(
                1L,
                BigDecimal.valueOf(100),
                LocalDate.now().plusDays(1)
        );

        var invalidRequest = new PaymentRequestDto(
                1L,
                BigDecimal.valueOf(100),
                LocalDate.now().minusDays(1)
        );

        var bankInfoResponse = new EmployeeBankInfoDto(
                "LastName",
                "FirstName",
                VALID_IBAN
        );

        when(employeeClient.getBankInfoByEmployeeId(anyLong())).thenReturn(bankInfoResponse);

        var result = paymentService.createPayment(validRequest);

        assertNotNull(result);
        assertNotNull(result.id());
        assertNotNull(result.updatedAt());
        assertEquals(validRequest.employeeId(), result.employeeId());
        assertEquals(validRequest.amount(), result.amount());
        assertEquals(validRequest.date(), result.paymentDate());
        assertEquals(PaymentStatus.PENDING, result.status());
        assertEquals(VALID_IBAN, result.IBAN());

        assertThrows(InvalidPaymentDateException.class,
                () -> paymentService.createPayment(invalidRequest));
    }

    @Test
    void getAll() {
        var result = paymentService.getAll();

        assertNotNull(result);
        assertEquals(4, result.size());
    }

    @Test
    void getById() {
        var result = paymentService.getById(EXISTING_ID);

        assertNotNull(result);
        assertThrows(PaymentNotFoundException.class,
                () -> paymentService.getById(NOT_EXISTING_ID));
    }
}