package com.lamashkevich.employee_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String position;

    private String region;

    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
