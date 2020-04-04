package com.example.medihelperapi.service

import com.example.medihelperapi.config.UserRole
import com.example.medihelperapi.dto.LoginChildDto
import com.example.medihelperapi.dto.LoginParentDto
import com.example.medihelperapi.dto.RegisterParentDto
import com.example.medihelperapi.model.Child
import com.example.medihelperapi.model.Parent
import com.example.medihelperapi.repository.ChildrenRepo
import com.example.medihelperapi.repository.ParentsRepo
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val parentsRepo: ParentsRepo,
    private val childrenRepo: ChildrenRepo,
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

    fun login(dto: LoginChildDto): String {
        val child = childrenRepo.findByConnectionKey(dto.connectionKey)
            .orElseThrow { ChildNotFound() }
        child.authToken = UUID.randomUUID().toString()
        val savedChild = childrenRepo.save(child)
        return savedChild.authToken
    }

    fun getRole(): UserRole {
        val userId = getCurrUserId()
        val parent = parentsRepo.findById(userId)
        if (parent.isPresent) {
            return UserRole.PARENT
        }
        val child = childrenRepo.findById(userId)
        if (child.isPresent) {
            return UserRole.CHILD
        }
        return UserRole.GUEST
    }

    fun expectParent(): Parent {
        val userId = getCurrUserId()
        val parent = parentsRepo.findByIdOrNull(userId)
        return parent ?: throw Forbidden()
    }

    fun expectChild(): Child {
        val userId = getCurrUserId()
        val child = childrenRepo.findByIdOrNull(userId)
        return child ?: throw Forbidden()
    }

    private fun getCurrUserId(): Long {
        val context = SecurityContextHolder.getContext()
        val principal = context.authentication.principal
        val user = principal as User
        return user.username.toLong()
    }
}