package com.imran.api.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import jakarta.validation.constraints.NotBlank
import lombok.AllArgsConstructor
import lombok.Data
import lombok.EqualsAndHashCode
import lombok.NoArgsConstructor
import lombok.ToString
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.Date

@Entity
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
class User (
    @Column(unique = true) @NotBlank var email: String,
    @Column(unique = true) @NotBlank var username: String,
    @NotBlank var password: String,
    @CreationTimestamp var createdAt: Date? = null,
    @UpdateTimestamp var updatedAt: Date? = null,
    @Id @GeneratedValue var id: Long? = null
)

