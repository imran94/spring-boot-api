package com.imran.api.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.imran.api.payload.request.RegistrationRequest
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*

@Entity
class User (
    @Column(unique = true) var email: String,
    @Column(unique = true) var username: String,
    @JsonIgnore var password: String,
    @Enumerated(EnumType.STRING) var role: Role,

    @JsonManagedReference
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "customer_id")
    var customer: Customer? = null,

    @JsonManagedReference
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "employee_id")
    var employee: Employee? = null,

    @JsonIgnore
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user")
    var tokens: MutableList<UserToken> = mutableListOf(),

    @CreationTimestamp var createdAt: Date? = null,
    @UpdateTimestamp var updatedAt: Date? = null,
    @Id @GeneratedValue var id: Long? = null
)

