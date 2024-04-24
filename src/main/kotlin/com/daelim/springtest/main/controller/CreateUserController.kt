package com.daelim.springtest.main.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class CreateUserController {

    data class UserRequest(
        val fullName: String,
        val email: String,
        val password: String
    )

    data class UserResponse(
        val fullName: String,
        val email: String,
        val password: String
    )

    @PostMapping("/user/create")
    fun createUser(@RequestBody userRequest: UserRequest): ResponseEntity<UserResponse> {
        // 여기서는 간단하게 입력된 값을 그대로 반환합니다.
        val userResponse = UserResponse(
            fullName = userRequest.fullName,
            email = userRequest.email,
            password = userRequest.password
        )
        return ResponseEntity.ok().body(userResponse)
    }
}
