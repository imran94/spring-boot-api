package com.imran.api.controllers

import com.imran.api.models.Artist
import com.imran.api.models.Track
import com.imran.api.repos.ArtistRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/artists")
class ArtistController(
    private val artistRepo: ArtistRepository
) {
    @GetMapping
    fun getAll(): ResponseEntity<MutableList<Artist>> = ResponseEntity.ok(artistRepo.findAll())

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) = artistRepo.findById(id).get()

    @PostMapping
    fun create(@RequestBody artist: Artist) = artistRepo.save(artist)

    @PutMapping
    fun update(@RequestBody artist: Artist) = artistRepo.save(artist)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = artistRepo.deleteById(id)
}