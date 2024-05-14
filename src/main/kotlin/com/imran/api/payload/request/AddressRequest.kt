package com.imran.api.payload.request

import jakarta.validation.constraints.NotBlank

data class AddressRequest(
    @NotBlank var street: String,
    @NotBlank var city: String,
    @NotBlank var region: String,
    @NotBlank var postalCode: String,
    @NotBlank var country: String,
)
