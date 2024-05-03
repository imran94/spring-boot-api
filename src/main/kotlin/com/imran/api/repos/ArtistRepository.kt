package com.imran.api.repos

import com.imran.api.models.Artist
import org.springframework.data.jpa.repository.JpaRepository

interface ArtistRepository: JpaRepository<Artist, Long>