package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.UserRegistrationDto
import com.example.medihelperapi.getCurrUser
import com.example.medihelperapi.model.RegisteredUser
import com.example.medihelperapi.service.RegisteredUserService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/users")
class UserController(private val registeredUserService: RegisteredUserService) {

    @PostMapping("/register")
    fun registerNewUser(@RequestBody registrationDto: UserRegistrationDto) {
        registeredUserService.register(registrationDto)
    }

    @PostMapping("/login")
    fun loginUser(@RequestParam("email") email: String, @RequestParam("password") password: String): String {
        return registeredUserService.login(email, password)
    }

    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    @GetMapping("/user", produces = ["application/json"])
    fun getUserDetails(): RegisteredUser {
        val user = SecurityContextHolder.getContext().getCurrUser()
        println(user.username)
        return registeredUserService.findByEmail(user.username)
    }
}