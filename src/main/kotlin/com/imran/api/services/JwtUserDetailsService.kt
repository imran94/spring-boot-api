package com.imran.api.services

import com.imran.api.JwtUserDetails
import com.imran.api.UserRoles
import com.imran.api.repos.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.Collections

@Service
class JwtUserDetailsService(val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User $username not found")
        val roles: MutableList<SimpleGrantedAuthority> = Collections.singletonList(SimpleGrantedAuthority(UserRoles.USER.name))
        return JwtUserDetails(user.id!!, username, user.hashCode().toString(), roles)
    }
}