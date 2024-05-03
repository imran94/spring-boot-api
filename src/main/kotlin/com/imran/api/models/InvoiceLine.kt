package com.imran.api.models

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
data class InvoiceLine(
    @Id @GeneratedValue var id: Long? = null,

    @JsonBackReference
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "invoice_id")
    var invoice: Invoice,

    @JsonBackReference
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "track_id")
    var track: Track,

    var quantity: Long,
    var unitPrice: BigDecimal
)
