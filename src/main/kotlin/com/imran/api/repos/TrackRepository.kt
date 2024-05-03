package com.imran.api.repos

import com.imran.api.models.Track
import org.springframework.data.jpa.repository.JpaRepository

interface TrackRepository: JpaRepository<Track, Long>