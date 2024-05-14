package com.imran.api.controllers

import com.imran.api.models.*
import com.imran.api.payload.request.CreateUserRequest
import com.imran.api.payload.response.ErrorResponse
import com.imran.api.repos.CustomerRepository
import com.imran.api.repos.EmployeeRepository
import com.imran.api.repos.UserRepository
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userRepo: UserRepository,
    private val employeeRepo: EmployeeRepository,
    private val customerRepo: CustomerRepository
) {
    @GetMapping
    fun getAll() = userRepo.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long)
    : ResponseEntity<Any> {
        val user = userRepo.findById(id)
        if (user.isEmpty) {
            return ResponseEntity(ErrorResponse("User Not Found"), HttpStatus.NOT_FOUND)
        }
        return ResponseEntity.ok(user.get())
    }

    @PostMapping
    fun create(@Valid @RequestBody request: CreateUserRequest):
            ResponseEntity<Any> {
        if ((request.role == Role.CUSTOMER && request.customer == null)
            || (request.role == Role.EMPLOYEE && request.employee == null)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse("Data for the selected role cannot be null"))
        }

        val user = User(
            email = request.email,
            username = request.username,
            password = BCryptPasswordEncoder().encode(request.password),
            role = request.role
        )

        val addressRequest = request.customer?.address ?: request.employee!!.address
        val address = Address(
            addressRequest.street,
            addressRequest.city,
            addressRequest.region,
            addressRequest.postalCode,
            addressRequest.country
        )
        if (request.role == Role.CUSTOMER) {
            var supportRep: Employee? = null
            if (request.customer!!.supportRepId != null) {
                val optEmployee = employeeRepo.findById(request.customer!!.supportRepId!!)
                if (optEmployee.isEmpty) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse("Support rep employee not found"))
                }
                supportRep = optEmployee.get()
            }

            user.customer = Customer(
                request.customer!!.firstName,
                request.customer!!.lastName,
                user,
                address,
                supportRep
            )
        } else {
            val reqEmployee = request.employee!!
            var manager: Employee? = null
            reqEmployee.managerId?.let {
                val optManager = employeeRepo.findById(it)
                if (optManager.isEmpty) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse("Manager employee employee not found"))
                }
                manager = optManager.get()
            }

            val subordinates = employeeRepo.findAllById(reqEmployee.subordinateIds)
            val customers = customerRepo.findAllById(reqEmployee.customerIds)

            user.employee = Employee(
                reqEmployee.firstName,
                reqEmployee.lastName,
                reqEmployee.title,
                reqEmployee.birthDate,
                reqEmployee.hireDate,
                reqEmployee.photoPath,
                address,
                user,
                manager,
                subordinates,
                customers
            )
        }

        val newlyCreatedUser = userRepo.save(user)

        return ResponseEntity.ok(newlyCreatedUser)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) =
        userRepo.deleteById(id)
}