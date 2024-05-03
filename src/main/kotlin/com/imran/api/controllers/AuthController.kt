package com.imran.api.controllers

import com.imran.api.mail.EmailService
import com.imran.api.models.Role
import com.imran.api.models.User
import com.imran.api.models.UserToken
import com.imran.api.payload.request.LoginRequest
import com.imran.api.payload.request.RefreshTokenRequest
import com.imran.api.payload.request.RegistrationRequest
import com.imran.api.payload.response.ErrorResponse
import com.imran.api.payload.response.LoginResponse
import com.imran.api.payload.response.RegisterResponse
import com.imran.api.repos.UserRepository
import com.imran.api.repos.UserTokenRepository
import com.imran.api.security.JwtTokenService
import com.imran.api.security.JwtUserDetailsService
import jakarta.validation.Valid
import org.apache.commons.lang3.RandomStringUtils
import org.apache.commons.lang3.time.DateUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar


@RestController
@RequestMapping("/auth")
class AuthController(
    private val userRepo: UserRepository,
    private val userTokenRepo: UserTokenRepository,
    private val jwtUserDetailsService: JwtUserDetailsService,
    private val jwtTokenService: JwtTokenService,
    private val emailService: EmailService
) {
    val passwordEncoder = BCryptPasswordEncoder()

    @PostMapping("/register")
    fun register(
        @Valid @RequestBody request: RegistrationRequest
    ): ResponseEntity<Any>{

        if ((request.role == Role.CUSTOMER && request.customer == null)
            || (request.role == Role.EMPLOYEE && request.employee == null)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse("Data for the selected role cannot be null"))
        }

        val user = User(
            email = request.email,
            username = request.username,
            password = passwordEncoder.encode(request.password),
            role = request.role
//            ,
//            customer = request.customer,
//            employee = request.employee
        )

        userRepo.save(user)
        emailService.sendEmail(
            user.email,
            "Verify your Email",
            "Thank you for signing up for the app, ${user.username}"
        )

        return ResponseEntity.ok(user)
//        return ResponseEntity.status(HttpStatus.CREATED)
//            .body(
//                RegisterResponse(
//                    user.id!!,
//                    user.email,
//                    user.username
//                )
//            )
    }

    @PostMapping("/authenticate")
    fun login(@Valid @RequestBody loginDetails: LoginRequest)
            : ResponseEntity<Any> {
        val user: User = userRepo.findByUsername(loginDetails.username!!)
            ?: userRepo.findByEmail(loginDetails.username!!)
            ?: return ResponseEntity(ErrorResponse("Username not found"), HttpStatus.UNAUTHORIZED)

        if (!passwordEncoder.matches(loginDetails.password, user.password)) {
            return ResponseEntity(ErrorResponse("Incorrect password"), HttpStatus.UNAUTHORIZED)
        }

        val userDetails = jwtUserDetailsService.loadUserByUsername(user.username)
        val accessToken = jwtTokenService.generateToken(userDetails)
        val refreshToken = RandomStringUtils.randomAlphanumeric(30)
        val userToken = UserToken(
            accessToken,
            refreshToken,
            LocalDateTime.now().plusMinutes(2),
            user
        )
        userTokenRepo.save(userToken)
        return ResponseEntity.ok(
            LoginResponse(
                user.email,
                user.username,
                accessToken,
                refreshToken,
                userToken.expiresAt
            )
        )
    }

    @PostMapping("/refresh-token")
    fun refreshToken(@Valid @RequestBody request: RefreshTokenRequest)
            : ResponseEntity<Any> {
        val userToken = userTokenRepo.findByRefreshToken(request.refreshToken)
            ?: return ResponseEntity(ErrorResponse("Token not found"), HttpStatus.UNAUTHORIZED)

        val userDetails = jwtUserDetailsService.loadUserByUsername(userToken.user.username)
        userToken.accessToken = jwtTokenService.generateToken(userDetails)
        userToken.expiresAt = LocalDateTime.now().plusMinutes(2)
        userTokenRepo.save(userToken)
        return ResponseEntity.ok(
            LoginResponse(
                userToken.user.email,
                userToken.user.username,
                userToken.accessToken,
                userToken.refreshToken,
                userToken.expiresAt
            )
        )
    }
}