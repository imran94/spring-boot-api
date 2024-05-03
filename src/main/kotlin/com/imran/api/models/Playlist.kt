package com.imran.api.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany

@Entity
data class Playlist(
    @Id @GeneratedValue var id: Long? = null,
    var name: String,

    @ManyToMany(mappedBy = "playlists")
    var tracks: MutableList<Track> = mutableListOf()
)
