package com.imran.api.repos

import com.imran.api.models.Genre
import org.springframework.data.jpa.repository.JpaRepository

interface GenreRepository: JpaRepository<Genre, Long>