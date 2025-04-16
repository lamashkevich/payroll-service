package com.lamashkevich.employee_service.service;

import com.lamashkevich.employee_service.dto.EmployeeCreateAndUpdateDto;
import com.lamashkevich.employee_service.dto.EmployeeDto;
import com.lamashkevich.employee_service.entity.Employee;
import com.lamashkevich.employee_service.exception.EmployeeNotFoundException;
import com.lamashkevich.employee_service.mapper.EmployeeMapper;
import com.lamashkevich.employee_service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;


    public List<EmployeeDto> findAll() {
        log.info("Getting all employees");
        return employeeRepository.findAll().stream()
                .map(employeeMapper::employeeToEmployeeDto)
                .toList();
    }

    public EmployeeDto findById(Long id) {
        log.info("Getting employee with id: {}", id);
        return employeeRepository
                .findById(id)
                .map(employeeMapper::employeeToEmployeeDto)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Transactional
    public EmployeeDto create(EmployeeCreateAndUpdateDto employeeDto) {
        log.info("Creating new employee: {}", employeeDto);
        return Optional.of(employeeDto)
                .map(employeeMapper::employeeCreateDtoToEmployee)
                .map(employeeRepository::save)
                .map(employeeMapper::employeeToEmployeeDto)
                .orElseThrow();
    }

    @Transactional
    public EmployeeDto updateById(Long id, EmployeeCreateAndUpdateDto employeeDto) {
        log.info("Updating employee with id {} using data: {}", id, employeeDto);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        employeeMapper.employeeCreateDtoToEmployee(employee, employeeDto);
        employeeRepository.save(employee);

        return employeeMapper.employeeToEmployeeDto(employee);
    }

    @Transactional
    public void deleteById(Long id) {
        log.info("Deleting employee with id {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        employeeRepository.delete(employee);
    }

}
