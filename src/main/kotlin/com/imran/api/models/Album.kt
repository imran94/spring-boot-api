package com.imran.api.models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
data class Album (
    @Id @GeneratedValue var id: Long? = null,
    var title: String,

    @JsonBackReference
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "artist_id")
    var artist: Artist,

    @JsonManagedReference
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "album")
    var tracks: MutableList<Track> = mutableListOf()
)
