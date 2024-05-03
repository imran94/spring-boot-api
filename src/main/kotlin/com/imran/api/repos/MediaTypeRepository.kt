package com.imran.api.repos

import com.imran.api.models.MediaType
import org.springframework.data.jpa.repository.JpaRepository

interface MediaTypeRepository: JpaRepository<MediaType, Long>