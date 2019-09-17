package com.example.medihelperapi.controller

import com.example.medihelperapi.model.AppUser
import com.example.medihelperapi.service.UserService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.lang.RuntimeException

@RestController
class UserController(private val userService: UserService) {

    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    @GetMapping("/api/users/user/{id}", produces = ["application/json"])
    fun getUserDetails(@PathVariable("id") userID: Long): AppUser {
        return userService.findByID(userID).map { user -> user }.orElseThrow { RuntimeException("UserNotFound") }
    }
}