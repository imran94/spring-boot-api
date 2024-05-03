package com.imran.api.repos

import com.imran.api.models.InvoiceLine
import org.springframework.data.jpa.repository.JpaRepository

interface InvoiceLineRepository: JpaRepository<InvoiceLine, Long>