package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.ConnectedPersonDto
import com.example.medihelperapi.dto.LoginInputDto
import com.example.medihelperapi.dto.LoginResponseDto
import com.example.medihelperapi.dto.RegisterInputDto
import com.example.medihelperapi.service.AuthenticationService
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/authentication")
class AuthenticationController(private val authenticationService: AuthenticationService) {

    @ApiResponses(ApiResponse(code = 409, message = "User with given email already exists in database"))
    @PostMapping("/register")
    fun registerNewUser(@RequestBody registerInputDto: RegisterInputDto) {
        authenticationService.register(registerInputDto)
    }

    @ApiResponses(
            ApiResponse(code = 404, message = "User not found in database"),
            ApiResponse(code = 322, message = "Incorrect user credentials")
    )
    @PostMapping("/login")
    fun loginUser(@RequestBody loginInputDto: LoginInputDto): LoginResponseDto {
        return authenticationService.login(loginInputDto)
    }

    @ApiResponses(ApiResponse(code = 404, message = "Person not found in database"))
    @GetMapping("/patron-connect")
    fun patronConnect(@RequestParam connectionKey: String): ConnectedPersonDto  {
        return authenticationService.patronConnect(connectionKey)
    }
}