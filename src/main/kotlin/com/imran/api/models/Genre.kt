package com.imran.api.models

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
data class Genre(
    @Id @GeneratedValue var id: Long? = null,
    var name: String,

    @JsonManagedReference
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "genre")
    var tracks: MutableList<Track> = mutableListOf()
)
