package com.imran.api.models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToOne

@Entity
class Address(
    var street: String,
    var city: String,
    var region: String,
    var postalCode: String,
    var country: String,

    @JsonBackReference
    @OneToOne(cascade = [CascadeType.ALL], mappedBy = "address")
    var employee: Employee? = null,

    @JsonBackReference
    @OneToOne(cascade = [CascadeType.ALL], mappedBy = "address")
    var customer: Customer? = null,

    @JsonBackReference
    @OneToOne(cascade = [CascadeType.ALL], mappedBy = "address")
    var invoice: Invoice? = null,

    @Id @GeneratedValue var id: Long? = null
)