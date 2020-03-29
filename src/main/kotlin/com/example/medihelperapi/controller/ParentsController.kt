package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.LoginParentDto
import com.example.medihelperapi.dto.RegisterParentDto
import com.example.medihelperapi.service.ParentsService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/parents")
class ParentsController(
    private val parentsService: ParentsService
) {

    @PostMapping("/register")
    fun registerParent(@RequestBody registerParentDto: RegisterParentDto): String {
        return parentsService.register(registerParentDto)
    }

    @PostMapping("/login")
    fun loginParent(@RequestBody loginParentDto: LoginParentDto): String {
        return parentsService.login(loginParentDto)
    }
}