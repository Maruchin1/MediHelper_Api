package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.PersonProfileDataDto
import com.example.medihelperapi.dto.UserCredentialsDto
import com.example.medihelperapi.service.AuthenticationService
import com.example.medihelperapi.service.RegisteredUserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/authentication")
class AuthenticationController(private val authenticationService: AuthenticationService) {

    @PostMapping("/register")
    fun registerNewUser(@RequestBody userCredentialsDto: UserCredentialsDto) {
        authenticationService.register(userCredentialsDto)
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody userCredentialsDto: UserCredentialsDto): String {
        return authenticationService.login(userCredentialsDto)
    }

    @GetMapping("/patron-connect")
    fun patronConnect(@RequestParam connectionKey: String): PersonProfileDataDto = authenticationService.patronConnect(connectionKey)
}