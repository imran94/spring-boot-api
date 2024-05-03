package com.imran.api.repos

import com.imran.api.models.Playlist
import org.springframework.data.jpa.repository.JpaRepository

interface PlaylistRepository: JpaRepository<Playlist, Long>