package com.daelim.springtest.main.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.random.Random

@RestController
@RequestMapping("/api")
class UserController {

    data class UserRequest(
        val email: String,
        val password: String
    )

    data class UserResponse(
        val Name: String,
        val email: String,
        val password: String
    )

    @PostMapping("/user/login")
    fun loginUser(@RequestBody userRequest: UserRequest): ResponseEntity<UserResponse> {
        val userResponse = UserResponse(
            Name = "string",
            email = userRequest.email,
            password = userRequest.password
        )
        return ResponseEntity.ok().body(userResponse)
    }

    @PostMapping("/user/create")
    fun createUser(@RequestBody userRequest: UserRequest): ResponseEntity<UserResponse> {
        val userResponse = UserResponse(
            Name = userRequest.email,
            email = userRequest.email,
            password = userRequest.password
        )
        return ResponseEntity.ok().body(userResponse)
    }
}
