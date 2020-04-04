package com.example.medihelperapi.service

import com.example.medihelperapi.dto.GetParentDto
import com.example.medihelperapi.dto.LoginParentDto
import com.example.medihelperapi.dto.RegisterParentDto
import com.example.medihelperapi.model.Parent
import com.example.medihelperapi.repository.ParentsRepo
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class ParentsService(
    private val parentsRepo: ParentsRepo,
    private val userService: UserService
) {

    fun getParent(): GetParentDto {
        val parent = userService.expectParent()
        return GetParentDto(parent)
    }
}