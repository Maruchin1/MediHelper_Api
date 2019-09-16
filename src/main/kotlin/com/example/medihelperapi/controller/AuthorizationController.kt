package com.example.medihelperapi.controller

import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/authorization")
class AuthorizationController {

    @ApiOperation(value = "Zarejestruj użytkownika")
    @PostMapping("/register")
    fun register(): ResponseEntity<Void> {
        return ResponseEntity.ok().build()
    }
}