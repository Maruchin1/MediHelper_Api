package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.LoginParentDto
import com.example.medihelperapi.dto.RegisterParentDto
import com.example.medihelperapi.service.ParentsService
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:4200"], maxAge = 3600)
@RestController
class ParentsController(
    private val parentsService: ParentsService
) {

    @PostMapping("/parents/register")
    fun registerParent(@RequestBody registerParentDto: RegisterParentDto): String {
        return parentsService.register(registerParentDto)
    }

    @PostMapping("/parents/login")
    fun loginParent(@RequestBody loginParentDto: LoginParentDto): String {
        return parentsService.login(loginParentDto)
    }
}