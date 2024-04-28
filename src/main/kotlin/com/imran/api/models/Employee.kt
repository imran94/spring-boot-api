package com.imran.api.models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*

@Entity
data class Employee(
    @Column(nullable = false)
    @NotBlank var name: String?,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotBlank
    var titleOfCourtesy: ETitle?,
    @Column(nullable = false) @NotBlank var birthDate: Date?,
    @Column(nullable = false) @NotBlank var hireDate: Date?,

    @JsonManagedReference
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "address_id")
    var address: Address?,

    var photoPath: String? = null,

    @JsonBackReference
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "manager")
    var manager: Employee? = null,

    @JsonManagedReference
    @OneToMany(mappedBy = "manager")
    var subordinates: MutableList<Employee> = mutableListOf(),

    @CreationTimestamp var createdAt: Date? = null,
    @UpdateTimestamp var updatedAt: Date? = null,
    @Id @GeneratedValue var id: Long? = null
)