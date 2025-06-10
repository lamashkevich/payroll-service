package com.lamashkevich.tax_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "tax_rates",
        uniqueConstraints = @UniqueConstraint(columnNames = {"position", "region"})
)
public class TaxRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String region;

    private String position;

    private BigDecimal totalRate;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
