package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.UserCredentialsDto
import com.example.medihelperapi.getCurrUser
import com.example.medihelperapi.model.RegisteredUser
import com.example.medihelperapi.service.RegisteredUserService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/registered-users")
class RegisteredUserController(private val registeredUserService: RegisteredUserService) {

    @PostMapping("/register")
    fun registerNewUser(@RequestBody userCredentials: UserCredentialsDto) {
        registeredUserService.register(userCredentials)
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody userCredentials: UserCredentialsDto): String {
        return registeredUserService.login(userCredentials)
    }

    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    @GetMapping("/user", produces = ["application/json"])
    fun getUserDetails(): RegisteredUser {
        val user = SecurityContextHolder.getContext().getCurrUser()
        println(user.username)
        return registeredUserService.findByEmail(user.username)
    }
}