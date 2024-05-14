package com.imran.api.controllers

import com.imran.api.models.Album
import com.imran.api.models.Track
import com.imran.api.repos.AlbumRepository
import com.imran.api.repos.ArtistRepository
import com.imran.api.repos.TrackRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/albums")
class AlbumController(
    private val trackRepo: TrackRepository,
    private val albumRepo: AlbumRepository,
    private val artistRepo: ArtistRepository
) {
    @GetMapping
    fun getAll() = albumRepo.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) = albumRepo.findById(id).get()

    @PostMapping
    fun create(@RequestBody request: AlbumRequest) = albumRepo.save(
        Album(
            title = request.title,
            artist = artistRepo.findById(request.artistId).get()
        )
    )

    @PutMapping
    fun update(@RequestBody album: Album) = albumRepo.save(album)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = albumRepo.deleteById(id)

    data class AlbumRequest(var title: String, var artistId: Long)
}