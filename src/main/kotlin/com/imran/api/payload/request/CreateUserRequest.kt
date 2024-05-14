package com.imran.api.payload.request

import com.imran.api.models.Customer
import com.imran.api.models.Employee
import com.imran.api.models.Role
import jakarta.validation.constraints.NotBlank

data class CreateUserRequest(
    @NotBlank val email: String,
    @NotBlank val username: String,
    @NotBlank val password: String,
    @NotBlank val role: Role,

    var customer: CustomerRequest?,
    var employee: EmployeeRequest?
)
