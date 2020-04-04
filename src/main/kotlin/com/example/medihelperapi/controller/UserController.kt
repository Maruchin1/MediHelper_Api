package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.LoginChildDto
import com.example.medihelperapi.dto.LoginParentDto
import com.example.medihelperapi.dto.RegisterParentDto
import com.example.medihelperapi.service.UserService
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:4200"], maxAge = 3600)
@RestController
class UserController(
    private val userService: UserService
) {

    @PostMapping("/users/register-parent")
    fun registerParent(@RequestBody registerParentDto: RegisterParentDto): String {
        return userService.register(registerParentDto)
    }

    @PostMapping("/users/login-parent")
    fun loginParent(@RequestBody loginParentDto: LoginParentDto): String {
        return userService.login(loginParentDto)
    }

    @PostMapping("/users/login-child")
    fun loginChild(@RequestBody loginChildDto: LoginChildDto): String {
        return userService.login(loginChildDto)
    }

    @GetMapping("/users/role")
    fun getUserRole(): String {
        return userService.getRole().toString()
    }

}