package com.lamashkevich.employee_service.service;

import com.lamashkevich.employee_service.dto.EmployeeCreateAndUpdateDto;
import com.lamashkevich.employee_service.entity.EmploymentType;
import com.lamashkevich.employee_service.exception.EmployeeNotFoundException;
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
class EmployeeServiceTest {

    @Container
    public static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17.4");

    private static final String VALID_IBAN = "GB33BUKB20201555555555";
    private static final long EXISTING_EMPLOYEE_ID = 1L;
    private static final long NON_EXISTING_EMPLOYEE_ID = 6L;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private EmployeeService employeeService;

    @Test
    void findAll() {
        var employees = employeeService.findAll();
        assertEquals(5, employees.size());
    }

    @Test
    void findById() {
        assertNotNull(employeeService.findById(EXISTING_EMPLOYEE_ID));
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.findById(NON_EXISTING_EMPLOYEE_ID));
    }

    @Test
    void create() {
        var employeeDto = new EmployeeCreateAndUpdateDto(
                "First",
                "Last",
                "Developer",
                "Minsk",
                BigDecimal.valueOf(9999),
                VALID_IBAN,
                EmploymentType.FULL_TIME
        );

        var result = employeeService.create(employeeDto);

        assertNotNull(result);
        assertEquals(employeeDto.IBAN(), result.IBAN());
        assertEquals(employeeDto.firstName(), result.firstName());
        assertEquals(employeeDto.lastName(), result.lastName());
        assertEquals(employeeDto.position(), result.position());
        assertEquals(employeeDto.salary(), result.salary());
        assertEquals(employeeDto.employmentType(), result.employmentType());
    }

    @Test
    void updateById() {
        var employeeDto = new EmployeeCreateAndUpdateDto(
                "First",
                "Last",
                "position",
                "position",
                BigDecimal.valueOf(9999),
                VALID_IBAN,
                EmploymentType.FULL_TIME
        );

        var result = employeeService.updateById(EXISTING_EMPLOYEE_ID, employeeDto);

        assertNotNull(result);
        assertEquals(VALID_IBAN, result.IBAN());
        assertEquals(EXISTING_EMPLOYEE_ID, result.id());
        assertEquals(employeeDto.firstName(), result.firstName());
        assertEquals(employeeDto.lastName(), result.lastName());
        assertEquals(employeeDto.position(), result.position());
        assertEquals(employeeDto.salary(), result.salary());
        assertEquals(employeeDto.employmentType(), result.employmentType());

        assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.updateById(NON_EXISTING_EMPLOYEE_ID, employeeDto));
    }

    @Test
    void deleteById() {
        employeeService.deleteById(EXISTING_EMPLOYEE_ID);

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteById(EXISTING_EMPLOYEE_ID));

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteById(NON_EXISTING_EMPLOYEE_ID));
    }

    @Test
    void getBankInfoDtoById() {
        var result = employeeService.getBankInfoDtoById(EXISTING_EMPLOYEE_ID);

        assertNotNull(result);
        assertNotNull(result.firstName());
        assertNotNull(result.lastName());
        assertEquals(VALID_IBAN, result.IBAN());
    }
}