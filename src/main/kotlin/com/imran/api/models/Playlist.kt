package com.imran.api.models

import jakarta.persistence.*

@Entity
data class Playlist(
    @Id @GeneratedValue var id: Long? = null,
    var name: String,

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "playlist_track",
        joinColumns = [JoinColumn(
            name = "playlist_id",
            referencedColumnName = "id"
        )],
        inverseJoinColumns = [JoinColumn(
            name = "track_id",
            referencedColumnName = "id"
        )]
    )
    var tracks: MutableList<Track> = mutableListOf()
)
