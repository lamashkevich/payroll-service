package com.lamashkevich.employee_service.repository;

import com.lamashkevich.employee_service.dto.EmployeeBankInfoDto;
import com.lamashkevich.employee_service.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<EmployeeBankInfoDto> findEmployeeBankInfoById(Long id);

    Optional<Employee> findByEmail(String email);

}
