package com.imran.api.payload.request

import jakarta.validation.constraints.NotBlank
import lombok.EqualsAndHashCode
import lombok.NoArgsConstructor
import lombok.ToString

@ToString
@EqualsAndHashCode
@NoArgsConstructor
class LoginRequest(
    @NotBlank var username: String? = null,
    @NotBlank var password: String? = null
)