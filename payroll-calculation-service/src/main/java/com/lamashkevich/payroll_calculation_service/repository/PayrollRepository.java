package com.lamashkevich.payroll_calculation_service.repository;

import com.lamashkevich.payroll_calculation_service.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {
}
