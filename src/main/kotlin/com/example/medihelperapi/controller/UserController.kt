package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.LoginChildDto
import com.example.medihelperapi.dto.LoginParentDto
import com.example.medihelperapi.dto.RegisterParentDto
import com.example.medihelperapi.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userService: UserService
) {

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PostMapping("/users/register-parent")
    fun registerParent(@RequestBody registerParentDto: RegisterParentDto): String {
        return userService.register(registerParentDto)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PostMapping("/users/login-parent")
    fun loginParent(@RequestBody loginParentDto: LoginParentDto): String {
        return userService.login(loginParentDto)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PostMapping("/users/login-child")
    fun loginChild(@RequestBody loginChildDto: LoginChildDto): String {
        return userService.login(loginChildDto)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping("/users/role")
    fun getUserRole(): String {
        return userService.getRole().toString()
    }

}