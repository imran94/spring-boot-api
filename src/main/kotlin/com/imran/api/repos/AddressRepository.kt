package com.imran.api.repos

import com.imran.api.models.Address
import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepository: JpaRepository<Address, Long>