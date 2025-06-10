package com.lamashkevich.tax_service.service;

import com.lamashkevich.tax_service.dto.TaxRateRequestDto;
import com.lamashkevich.tax_service.exception.TaxRateNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@Sql("/sql/init.sql")
@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class TaxServiceTest {

    @Container
    public static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17.4");

    private static final String EXISTING_REGION = "Minsk";
    private static final String EXISTING_POSITION = "Developer";
    private static final String NOT_EXISTING_POSITION = "HR";

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private TaxService taxService;

    @Test
    void getAll() {
        var result = taxService.getAll();

        assertNotNull(result);
        assertEquals(4, result.size());
    }

    @Test
    void getTotalRateByRegionAndPosition() {
        var result = taxService.getTotalRateByRegionAndPosition(EXISTING_REGION, EXISTING_POSITION);

        assertNotNull(result);
        assertEquals(0, BigDecimal.valueOf(0.10).compareTo(result));
        assertThrows(TaxRateNotFoundException.class,
                () -> taxService.getTotalRateByRegionAndPosition(EXISTING_REGION, NOT_EXISTING_POSITION));
    }

    @Test
    void createOrUpdate_whenTaxRateAlreadyExist() {
        var newTaxRate = BigDecimal.valueOf(0.20);
        var existingRequest = new TaxRateRequestDto(
                EXISTING_REGION,
                EXISTING_POSITION,
                newTaxRate
        );

        var result = taxService.createOrUpdate(existingRequest);

        assertNotNull(result);
        assertEquals(EXISTING_POSITION, result.position());
        assertEquals(EXISTING_REGION, result.region());
        assertEquals(0, newTaxRate.compareTo(result.totalRate()));
    }

    @Test
    void createOrUpdate_whenTaxRateNotExist() {
        var newTaxRate = BigDecimal.valueOf(0.20);
        var existingRequest = new TaxRateRequestDto(
                EXISTING_REGION,
                NOT_EXISTING_POSITION,
                newTaxRate
        );

        var result = taxService.createOrUpdate(existingRequest);

        assertNotNull(result);
        assertEquals(NOT_EXISTING_POSITION, result.position());
        assertEquals(EXISTING_REGION, result.region());
        assertEquals(0, newTaxRate.compareTo(result.totalRate()));
    }
}