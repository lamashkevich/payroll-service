package com.lamashkevich.employee_service.controller;

import com.lamashkevich.employee_service.dto.EmployeeDto;
import com.lamashkevich.employee_service.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees/profile")
@PreAuthorize("hasRole('EMPLOYEE')")
public class ProfileController {

    private final EmployeeService employeeService;

    @GetMapping
    public EmployeeDto get(Authentication auth) {
        String email = auth.getName();
        return employeeService.findByEmail(email);
    }

}
