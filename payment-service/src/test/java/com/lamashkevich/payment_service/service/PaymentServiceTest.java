package com.lamashkevich.payment_service.service;

import com.lamashkevich.payment_service.client.EmployeeClient;
import com.lamashkevich.payment_service.dto.EmployeeBankInfoDto;
import com.lamashkevich.payment_service.dto.PaymentFilter;
import com.lamashkevich.payment_service.dto.PaymentRequestDto;
import com.lamashkevich.payment_service.entity.PaymentStatus;
import com.lamashkevich.payment_service.exception.InvalidPaymentDateException;
import com.lamashkevich.payment_service.exception.PaymentNotFoundException;
import com.lamashkevich.payment_service.utils.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@Sql("/sql/init.sql")
class PaymentServiceTest extends BaseIntegrationTest {

    private static final String VALID_IBAN = "GB33BUKB20201555555555";
    private static final long EXISTING_ID = 1L;
    private static final long NOT_EXISTING_ID = 5L;

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
    void getById() {
        var result = paymentService.getById(EXISTING_ID);

        assertNotNull(result);
        assertThrows(PaymentNotFoundException.class,
                () -> paymentService.getById(NOT_EXISTING_ID));
    }

    @Test
    void getAllByFilter() {
        var stringDate = "2025-05-14";
        var minAmount = BigDecimal.valueOf(150);
        var maxAmount = BigDecimal.valueOf(350);
        var startDate = LocalDate.parse(stringDate);
        var endDate = LocalDate.parse(stringDate);

        var filter = new PaymentFilter(minAmount, maxAmount, startDate, endDate);

        var result = paymentService.getAllByFilter(filter);
        assertNotNull(result);
        assertEquals(1, result.size());

        var first = result.getFirst();
        assertEquals(3L, first.id());
        assertEquals(stringDate, first.updatedAt().toLocalDate().toString());
        assertTrue(first.amount().compareTo((maxAmount)) <= 0);
        assertTrue(first.amount().compareTo((minAmount)) >= 0);
    }

    @Test
    void getAllByFilter_whenFilterIsEmpty() {
        var filter = new PaymentFilter(null, null, null, null);

        var result = paymentService.getAllByFilter(filter);
        assertNotNull(result);
        assertEquals(4, result.size());
    }
}