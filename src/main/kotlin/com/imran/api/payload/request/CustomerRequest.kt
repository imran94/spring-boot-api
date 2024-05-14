package com.imran.api.payload.request

import jakarta.validation.constraints.NotBlank

data class CustomerRequest(
    @NotBlank var firstName: String,
    @NotBlank var lastName: String,

    val address: AddressRequest,
    var supportRepId: Long?,

)
