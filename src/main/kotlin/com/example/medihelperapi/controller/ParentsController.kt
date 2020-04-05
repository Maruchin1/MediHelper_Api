package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.GetParentDto
import com.example.medihelperapi.dto.LoginParentDto
import com.example.medihelperapi.dto.RegisterParentDto
import com.example.medihelperapi.service.ParentsService
import org.springframework.web.bind.annotation.*

@RestController
class ParentsController(
    private val parentsService: ParentsService
) {

    @GetMapping("/parents/byAuthToken")
    fun getLoggedParent(): GetParentDto {
        return parentsService.getParent()
    }
}