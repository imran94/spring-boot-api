package com.imran.api.models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*

@Entity
class Customer(
    var firstName: String?,
    var lastName: String?,

    @JsonBackReference
    @OneToOne(cascade = [CascadeType.ALL], mappedBy = "customer")
    var user: User,

    @JsonManagedReference
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "address_id")
    var address: Address,

    @JsonBackReference
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "support_rep_id")
    var supportRep: Employee?,

    @JsonManagedReference
    @OneToMany(mappedBy = "customer")
    var invoices: MutableList<Invoice> = mutableListOf(),

    @Id @GeneratedValue var id: Long? = null
)
