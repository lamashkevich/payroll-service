package com.lamashkevich.payroll_calculation_service.service;

import com.lamashkevich.payroll_calculation_service.client.TaxClient;
import com.lamashkevich.payroll_calculation_service.dto.PayrollCalculationRequestDTO;
import com.lamashkevich.payroll_calculation_service.entity.PaymentType;
import com.lamashkevich.payroll_calculation_service.event.PayrollCalculatedEvent;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@EmbeddedKafka
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

    @Value("${kafka.topics.payroll-calculated}")
    private String payrollCalculatedTopic;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Autowired
    private PayrollCalculationService payrollCalculationService;

    @MockitoBean
    private TaxClient taxClient;

    private Consumer<String, PayrollCalculatedEvent> consumer;

    @BeforeEach
    void setupConsumer() {
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        consumerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        consumerProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, PayrollCalculatedEvent.class.getName());

        consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Collections.singletonList(payrollCalculatedTopic));
    }

    @AfterEach
    void tearDown() {
        consumer.close();
    }

    @Test
    void calculate() {
        var taxRate = BigDecimal.valueOf(0.10);
        var id = 1L;
        var baseSalary = BigDecimal.valueOf(100.25);
        var bonuses = List.of(BigDecimal.valueOf(10.5), BigDecimal.valueOf(5.25));
        var deductions = List.of(BigDecimal.valueOf(10));
        var position = "Manager";
        var region = "Minsk";
        var paymentType = PaymentType.MONTHLY;

        var request = new PayrollCalculationRequestDTO(
                id, baseSalary, bonuses, deductions, position, region, paymentType);

        var expectedTotalBonuses = BigDecimal.valueOf(15.75);
        var expectedTotalDeductions = BigDecimal.valueOf(10.00);
        var expectedTaxAmount = BigDecimal.valueOf(10.60);
        var expectedFinalSalary = BigDecimal.valueOf(95.40);

        when(taxClient.getTaxRate(anyString(), anyString())).thenReturn(taxRate);

        var result = payrollCalculationService.calculate(request);

        assertNotNull(result);
        assertEquals(request.paymentType(), result.paymentType());
        assertEquals(request.employeeId(), result.employeeId());
        assertEquals(request.baseSalary(), result.baseSalary());
        assertEquals(0, expectedTotalBonuses.compareTo(result.totalBonuses()));
        assertEquals(0, expectedTotalDeductions.compareTo(result.totalDeductions()));
        assertEquals(0, expectedTaxAmount.compareTo(result.taxAmount()));
        assertEquals(0, expectedFinalSalary.compareTo(result.finalSalary()));

        var record = KafkaTestUtils.getSingleRecord(consumer, payrollCalculatedTopic ,Duration.ofSeconds(10));
        assertEquals(id, record.value().getEmployeeId());
        assertEquals(0, expectedFinalSalary.compareTo(record.value().getAmount()));
    }
}