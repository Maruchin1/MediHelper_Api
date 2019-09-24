package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.registereduser.NewPasswordDto
import com.example.medihelperapi.dto.registereduser.UserCredentialsDto
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

    @PatchMapping("/change-password")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun changeUserPassword(@RequestBody newPassword: NewPasswordDto) {
        registeredUserService.changePassword(registeredUserService.getLoggedUser(), newPassword)
    }

    @GetMapping("/has-data")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun hasData(): Boolean {
        return registeredUserService.hasData(registeredUserService.getLoggedUser())
    }
}