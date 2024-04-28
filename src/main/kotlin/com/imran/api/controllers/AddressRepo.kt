package com.imran.api.controllers

import com.imran.api.models.Address
import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepo: JpaRepository<Address, Long>