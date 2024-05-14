package com.imran.api.payload.request

import jakarta.validation.constraints.NotBlank
import java.util.*

data class EmployeeRequest(
    @NotBlank var firstName: String,
    @NotBlank var lastName: String,
    @NotBlank var title: String,
    var birthDate: Date,
    var hireDate: Date,
    var photoPath: String?,

    var address: AddressRequest,
    var managerId: Long?,
    var subordinateIds: MutableList<Long> = mutableListOf(),
    var customerIds: MutableList<Long> = mutableListOf()
)
