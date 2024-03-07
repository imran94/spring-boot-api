package com.imran.api.controllers

import com.imran.api.models.User
import com.imran.api.payload.request.LoginRequest
import com.imran.api.payload.response.ErrorResponse
import com.imran.api.payload.response.LoginResponse
import com.imran.api.repos.UserRepository
import com.imran.api.services.JwtTokenService
import com.imran.api.services.JwtUserDetailsService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/auth")
class AuthController(
    private val userRepo: UserRepository,
    private val jwtUserDetailsService: JwtUserDetailsService,
    private val jwtTokenService: JwtTokenService
) {

    val passwordEncoder = BCryptPasswordEncoder()

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    fun register(@Valid @RequestBody user: User): User {
        user.password = passwordEncoder.encode(user.password)
        return userRepo.save(user)
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
        return ResponseEntity.ok(
            LoginResponse(
                user.email,
                user.username,
                jwtTokenService.generateToken(userDetails)
            )
        )
    }

    @GetMapping("/test")
    fun test() = "Nigga"
}