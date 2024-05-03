package com.imran.api.repos

import com.imran.api.models.Album
import org.springframework.data.jpa.repository.JpaRepository

interface AlbumRepository: JpaRepository<Album, Long>