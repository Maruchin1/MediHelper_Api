package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.ConnectedPersonDto
import com.example.medihelperapi.dto.LoginResponseDto
import com.example.medihelperapi.dto.UserCredentialsDto
import com.example.medihelperapi.service.AuthenticationService
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/authentication")
class AuthenticationController(private val authenticationService: AuthenticationService) {

    @ApiResponses(ApiResponse(code = 409, message = "User with given email already exists in database"))
    @PostMapping("/register")
    fun registerNewUser(@RequestBody userCredentialsDto: UserCredentialsDto) {
        authenticationService.register(userCredentialsDto)
    }

    @ApiResponses(
            ApiResponse(code = 404, message = "User not found in database"),
            ApiResponse(code = 322, message = "Incorrect user credentials")
    )
    @PostMapping("/login")
    fun loginUser(@RequestBody userCredentialsDto: UserCredentialsDto): LoginResponseDto {
        return authenticationService.login(userCredentialsDto)
    }

    @ApiResponses(ApiResponse(code = 404, message = "Person not found in database"))
    @GetMapping("/patron-connect")
    fun patronConnect(@RequestParam connectionKey: String): ConnectedPersonDto  {
        return authenticationService.patronConnect(connectionKey)
    }
}