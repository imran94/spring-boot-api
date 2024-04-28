package com.imran.api.models

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

@Entity
data class UserToken(

    @Column(unique = true, nullable = false)
    var accessToken: String,
    @Column(unique = true, nullable = false)
    var refreshToken: String,
    var expiresAt: LocalDateTime,

    @JsonBackReference
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id")
    var user: User,

    @Id @GeneratedValue var id: Long? = null

)
