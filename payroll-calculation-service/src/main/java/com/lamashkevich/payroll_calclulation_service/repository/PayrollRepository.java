package com.lamashkevich.payroll_calclulation_service.repository;

import com.lamashkevich.payroll_calclulation_service.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {
}
