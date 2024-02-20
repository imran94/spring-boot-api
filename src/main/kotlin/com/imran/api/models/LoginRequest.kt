package com.imran.api.models

import jakarta.validation.constraints.NotBlank
import lombok.AllArgsConstructor
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