package com.imran.api.payload.request

import com.imran.api.models.Role
import jakarta.validation.constraints.NotBlank

data class RegistrationRequest(
    @NotBlank val email: String,
    @NotBlank val username: String,
    @NotBlank val password: String,
    @NotBlank val role: Role
)
