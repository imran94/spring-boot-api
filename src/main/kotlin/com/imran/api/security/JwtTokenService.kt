package com.imran.api.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant


@Service
class JwtTokenService(
    env: Environment
) {
    @Value("\${app.jwtSecret}")
    private val jwtSecret: String = ""

    private val hmac512: Algorithm = Algorithm.HMAC512(env.getProperty("app.jwtSecret", "secret"))
    private val verifier: JWTVerifier = JWT.require(hmac512).build()

    fun generateToken(userDetails: UserDetails): String {
        val now = Instant.now()
        return JWT.create()
            .withSubject(userDetails.username)
            .withIssuer("app")
            .withIssuedAt(now)
            .withExpiresAt(now.plusMillis(JWT_TOKEN_VALIDITY.toMillis()))
            .sign(hmac512)
    }

    fun validateTokenAndGetUsername(token: String?): String? {
        return try {
            verifier.verify(token).subject
        } catch (verificationEx: JWTVerificationException) {
            null
        }
    }

    companion object {
        private val JWT_TOKEN_VALIDITY = Duration.ofHours(2)
    }
}