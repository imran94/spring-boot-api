package com.imran.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint


@SpringBootApplication
class ApiApplication

fun main(args: Array<String>) {
	runApplication<ApiApplication>(*args)
}

//@Bean
//@Throws(Exception::class)
//fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
//	return http
//		.authorizeHttpRequests { customizer ->
//			customizer
//				.requestMatchers("/api/login").permitAll()
//				.requestMatchers("/api/**").authenticated()
//				.anyRequest().denyAll()
//		}
//		.exceptionHandling { customizer: ExceptionHandlingConfigurer<HttpSecurity?> ->
//			customizer
//				.authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//		}
//		.build()
//}
