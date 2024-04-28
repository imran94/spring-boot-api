package com.imran.api.security

import com.imran.api.models.Role
import com.imran.api.repos.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtUserDetailsService(val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User $username not found")
        val roles: MutableList<SimpleGrantedAuthority> =
            Collections.singletonList(SimpleGrantedAuthority(user.role.name))
        return JwtUserDetails(
            user.id!!,
            username,
            user.hashCode().toString(),
            roles
        )
    }
}