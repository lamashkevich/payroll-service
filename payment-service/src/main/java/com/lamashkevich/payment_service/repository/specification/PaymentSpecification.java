package com.lamashkevich.payment_service.repository.specification;

import com.lamashkevich.payment_service.entity.Payment;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentSpecification {

    public static Specification<Payment> hasMinAmount(BigDecimal minAmount) {
        return (root, query, criteriaBuilder) -> {
            if (minAmount == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), minAmount);
        };
    }

    public static Specification<Payment> hasMaxAmount(BigDecimal maxAmount) {
        return (root, query, criteriaBuilder) -> {
            if (maxAmount == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("amount"), maxAmount);
        };
    }

    public static Specification<Payment> updatedAtBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null || endDate == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.between(root.get("updatedAt"), startDate, endDate.plusDays(1));
        };
    }

}
