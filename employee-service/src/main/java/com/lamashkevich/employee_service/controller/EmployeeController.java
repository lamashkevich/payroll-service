package com.lamashkevich.employee_service.controller;

import com.lamashkevich.employee_service.dto.EmployeeCreateAndUpdateDto;
import com.lamashkevich.employee_service.dto.EmployeeDto;
import com.lamashkevich.employee_service.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDto> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public EmployeeDto findById(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @PostMapping
    private EmployeeDto create(@RequestBody EmployeeCreateAndUpdateDto employeeDto) {
        return employeeService.create(employeeDto);
    }

    @PutMapping("/{id}")
    private EmployeeDto update(@PathVariable Long id,
                               @RequestBody EmployeeCreateAndUpdateDto employeeDto) {
        return employeeService.updateById(id, employeeDto);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id) {
        employeeService.deleteById(id);
    }

}
