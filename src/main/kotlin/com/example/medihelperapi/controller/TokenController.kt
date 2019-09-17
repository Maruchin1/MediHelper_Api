package com.example.medihelperapi.controller

import com.example.medihelperapi.service.UserService
import org.apache.commons.lang3.StringUtils
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TokenController(private val userService: UserService) {

    @PostMapping("/token")
    fun getToken(@RequestParam("email") email: String, @RequestParam("password") password: String): String {
        println("header = $AUTHORIZATION")
        val token = userService.login(email, password)
        if (StringUtils.isEmpty(token)) {
            return "no token found"
        }
        return token
    }
}