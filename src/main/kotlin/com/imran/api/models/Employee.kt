package com.imran.api.models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*

@Entity
class Employee(
    var firstName: String?,
    var lastName: String?,
    var title: String?,
    var birthDate: Date?,
    var hireDate: Date?,
    var photoPath: String? = null,

    @JsonManagedReference
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "address_id")
    var address: Address,

    @JsonBackReference
    @OneToOne(mappedBy = "employee")
    var user: User,

    @JsonBackReference
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "manager")
    var manager: Employee? = null,

    @JsonManagedReference
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "manager")
    var subordinates: MutableList<Employee> = mutableListOf(),

    @JsonManagedReference
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "supportRep")
    var customers: MutableList<Customer> = mutableListOf(),

    @CreationTimestamp var createdAt: Date? = null,
    @UpdateTimestamp var updatedAt: Date? = null,
    @Id @GeneratedValue var id: Long? = null
)