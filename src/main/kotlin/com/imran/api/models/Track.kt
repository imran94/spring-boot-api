package com.imran.api.models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
data class Track(
    @Id @GeneratedValue var id: Long? = null,
    var name: String,
    var composer: String,
    var milliseconds: Long,
    var bytes: Long,
    var unitPrice: BigDecimal,

    @JsonManagedReference
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "track")
    var invoiceLines: MutableList<InvoiceLine> = mutableListOf(),

    @JsonBackReference
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "album_id")
    var album: Album,

    @JsonBackReference
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "media_type_id")
    var mediaType: MediaType,

    @JsonBackReference
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "genre_id")
    var genre: Genre,

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
//    @JsonIgnoreProperties("tracks")
    var playlists: MutableList<Playlist> = mutableListOf()
)