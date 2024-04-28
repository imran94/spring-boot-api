package com.imran.api.payload.request

import jakarta.validation.constraints.NotBlank

data class RefreshTokenRequest(
    @NotBlank val refreshToken: String
)
