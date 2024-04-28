package com.imran.api.models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToOne

@Entity
data class Address(
    var street: String,
    var city: String,
    var region: String,
    var postalCode: String,
    var country: String,

    @JsonBackReference
    @OneToOne(mappedBy = "address")
    @JsonIgnore
    var employee: Employee?,
    @Id @GeneratedValue var id: Long? = null
)