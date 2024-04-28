package com.imran.api.repos

import com.imran.api.models.UserToken
import org.springframework.data.jpa.repository.JpaRepository

interface UserTokenRepository : JpaRepository<UserToken, Long> {
    fun findByAccessToken(accessToken: String): UserToken?
    fun findByRefreshToken(refreshToken: String): UserToken?
}