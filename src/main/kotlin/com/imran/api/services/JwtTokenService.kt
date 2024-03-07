package com.imran.api.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant


@Service
class JwtTokenService {

    private val hmac512: Algorithm = Algorithm.HMAC512("b313a21908df55c9e322e3c65a4b0b7561ab1594ca806b3affbc0d769a1290c1922aa6622587bea3c0c4d871470a6d06f54dbd20dbda84250e2741eb01f08e33")
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
        private val JWT_TOKEN_VALIDITY = Duration.ofMinutes(20)
    }
}