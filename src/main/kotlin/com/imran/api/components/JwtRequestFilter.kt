package com.imran.api.components

import com.imran.api.services.JwtTokenService
import com.imran.api.services.JwtUserDetailsService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtRequestFilter(
    val jwtTokenService: JwtTokenService,
    val jwtUserDetailsService: JwtUserDetailsService
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (header == null || !header.startsWith("Bearer ")) {
//            response.status = HttpStatus.UNAUTHORIZED.value()
//            response.flushBuffer()
            chain.doFilter(request, response)
            return
        }

        val token = header.substring(startIndex = 7)
        val username = jwtTokenService.validateTokenAndGetUsername(token)
        if (username == null) {
            val url = request.requestURL
            // validation failed or token expired
            response.status = HttpStatus.UNAUTHORIZED.value()
            response.flushBuffer()
//            chain.doFilter(request, response)
            return
        }

        // set user details on spring security context
        try {
            val userDetails = jwtUserDetailsService.loadUserByUsername(username)
            val authentication = UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.authorities
            )
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authentication
        } catch (_: UsernameNotFoundException) {

        } finally {
            chain.doFilter(request, response)
        }

    }
}