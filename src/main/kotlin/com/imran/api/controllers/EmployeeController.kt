package com.imran.api.controllers

import com.imran.api.models.Employee
import com.imran.api.repos.EmployeeRepository
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/employees")
class EmployeeController(
    private val employeeRepo: EmployeeRepository
) {
    @GetMapping
    fun getAll(): List<Employee> {
        val auth = SecurityContextHolder.getContext().authentication
        auth.principal
        return employeeRepo.findAll()
    }

    @PostMapping
    @Throws(Exception::class)
//    @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun save(@Valid @RequestBody employee: Employee): Employee {
        val user = SecurityContextHolder.getContext().authentication.principal
        println("Fuck Spring Boot")
        return employeeRepo.save(employee)
    }
}