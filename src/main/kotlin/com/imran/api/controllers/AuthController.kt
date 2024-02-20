package com.imran.api.controllers

import com.imran.api.models.HttpResponse
import com.imran.api.models.LoginRequest
import com.imran.api.models.LoginResponse
import com.imran.api.models.User
import com.imran.api.repos.UserRepository
import com.imran.api.services.JwtTokenService
import com.imran.api.services.JwtUserDetailsService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


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

    @PostMapping("/login")
    fun login(@Valid @RequestBody loginDetails: LoginRequest)
            : ResponseEntity<HttpResponse> {

        val user: User = userRepo.findByUsername(loginDetails.username!!)
            ?: userRepo.findByEmail(loginDetails.username!!)
            ?: return ResponseEntity(HttpResponse(error = "Username not found"), HttpStatus.UNAUTHORIZED)

        if (!passwordEncoder.matches(loginDetails.password, user.password)) {
            return ResponseEntity(HttpResponse(error = "Incorrect password"), HttpStatus.UNAUTHORIZED)
        }

        val userDetails = jwtUserDetailsService.loadUserByUsername(user.username)
        return ResponseEntity(
            HttpResponse(data = LoginResponse(user.email, user.username, jwtTokenService.generateToken(userDetails))),
            HttpStatus.OK
        )
    }
}