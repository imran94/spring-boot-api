package com.imran.api.configs

import com.imran.api.UserRoles
import com.imran.api.components.JwtRequestFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
class JwtSecurityConfig(val jwtRequestFilter: JwtRequestFilter) {
    @Bean
    fun passwordEncoder() = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(
        authenticationConfiguration: AuthenticationConfiguration
    ) = authenticationConfiguration.getAuthenticationManager()

    @Bean
    @Throws(Exception::class)
    fun configure(http: HttpSecurity): SecurityFilterChain {
        return http.cors(withDefaults())
            .csrf { csrf: CsrfConfigurer<HttpSecurity> -> csrf.disable() }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/auth/**").permitAll()
                    .anyRequest().hasAuthority(UserRoles.USER.name)
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}