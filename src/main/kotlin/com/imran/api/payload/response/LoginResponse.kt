package com.imran.api.payload.response

import java.time.LocalDateTime

class LoginResponse(
    val email: String,
    val username: String,
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: LocalDateTime
)