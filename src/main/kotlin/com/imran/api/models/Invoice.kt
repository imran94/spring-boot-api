package com.imran.api.models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.util.*

@Entity
data class Invoice(
    @Id @GeneratedValue var id: Long? = null,

    @JsonBackReference
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "customer_id")
    var customer: Customer,

    @CreationTimestamp var invoiceDate: Date? = null,

    @JsonManagedReference
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "address_id")
    var address: Address,

    @JsonManagedReference
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "invoice")
    var invoiceLines: MutableList<InvoiceLine> = mutableListOf(),

    var total: BigDecimal,
)
