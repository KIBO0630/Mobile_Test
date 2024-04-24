package com.daelim.springtest.main.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.regex.Pattern

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

    // 임시 데이터베이스 대신 사용할 Map
    private val userDatabase = mutableMapOf<String, UserResponse>()

    @PostMapping("/user/create")
    fun createUser(@RequestBody userRequest: UserRequest): ResponseEntity<*> {
        // 이메일 형식이 올바른지 확인합니다.
        if (!isEmailValid(userRequest.email)) {
            return ResponseEntity.badRequest().body("Invalid email format")
        }
        // 이메일 중복 체크
        if (userDatabase.containsKey(userRequest.email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists")
        }
        // 여기서는 간단하게 입력된 값을 그대로 반환합니다.
        val userResponse = UserResponse(
            fullName = userRequest.fullName,
            email = userRequest.email,
            password = userRequest.password
        )
        // 사용자 정보를 데이터베이스에 저장
        userDatabase[userRequest.email] = userResponse
        return ResponseEntity.ok().body(userResponse)
    }

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = ("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}\$")
        val pattern = Pattern.compile(emailRegex)
        return pattern.matcher(email).matches()
    }
}
