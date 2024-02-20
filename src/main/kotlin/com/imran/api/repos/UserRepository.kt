package com.imran.api.repos

import com.imran.api.models.User
import org.springframework.data.jpa.repository.JpaRepository


interface UserRepository : JpaRepository<User, Long> {

    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?
}