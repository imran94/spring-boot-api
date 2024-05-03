package com.imran.api.models

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
data class Artist (
    @Id @GeneratedValue var id: Long? = null,
    var name: String,

    @JsonManagedReference
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "artist")
    var albums: MutableList<Album> = mutableListOf()
)