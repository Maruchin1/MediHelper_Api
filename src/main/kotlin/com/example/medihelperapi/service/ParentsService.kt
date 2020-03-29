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
    private val passwordEncoder: PasswordEncoder
) {
    fun register(dto: RegisterParentDto): String {
        if (parentsRepo.existsByEmail(dto.email)) {
            throw ParentExists()
        }
        val newParent = Parent(
            userName = dto.name,
            email = dto.email,
            password = passwordEncoder.encode(dto.password),
            authToken = UUID.randomUUID().toString()
        )
        val savedParent = parentsRepo.save(newParent)
        return savedParent.authToken
    }

    fun login(dto: LoginParentDto): String {
        val parent = parentsRepo.findByEmail(dto.email)
            .orElseThrow { UserNotFound() }
        if (!passwordEncoder.matches(dto.password, parent.password)) {
            throw IncorrectCredentials()
        }
        return parent.authToken
    }

    fun getParent(authToken : String): GetParentDto{
        val parent = parentsRepo.findByAuthToken(authToken)
        if (parent.isPresent) {
            return GetParentDto(parent.get())
        }
        throw UserNotFound()
    }
}