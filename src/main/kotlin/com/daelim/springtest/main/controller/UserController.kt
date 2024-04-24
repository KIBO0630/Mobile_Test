package com.daelim.springtest.main.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class UserController {

    data class UserRequest(
        val email: String,
        val password: String
    )

    data class UserResponse(
        val fullName: String,
        val email: String,
        val password: String
    )

    // 임시 데이터베이스 대신 사용할 Map
    private val userDatabase = mutableMapOf<String, UserResponse>()

    @PostMapping("/user/login")
    fun loginUser(@RequestBody userRequest: UserRequest): ResponseEntity<*> {
        // 이메일과 비밀번호의 유효성을 확인합니다.
        if (!isValidUser(userRequest.email, userRequest.password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password")
        }
        // 여기서는 간단하게 입력된 값을 그대로 반환합니다.
        val userResponse = UserResponse(
            fullName = userRequest.email, // 여기서는 email을 사용하여 fullName을 설정했습니다. 실제로는 다른 방식으로 구현해야 할 것입니다.
            email = userRequest.email,
            password = userRequest.password
        )
        return ResponseEntity.ok().body(userResponse)
    }

    private fun isValidUser(email: String, password: String): Boolean {
        // 이메일과 비밀번호가 일치하는지 여부를 확인합니다.
        return userDatabase[email]?.password == password
    }
}
