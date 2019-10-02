package com.example.medihelperapi.service

import com.example.medihelperapi.dto.UserCredentialsDto
import com.example.medihelperapi.model.RegisteredUser
import com.example.medihelperapi.repository.RegisteredUserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
        private val registeredUserRepository: RegisteredUserRepository,
        private val passwordEncoder: PasswordEncoder
) {
    fun register(userCredentials: UserCredentialsDto) {
        if (registeredUserRepository.existsByEmail(userCredentials.email)) {
            throw RegisteredUserExistsException()
        }
        val newRegisteredUser = RegisteredUser(
                email = userCredentials.email,
                password = passwordEncoder.encode(userCredentials.password),
                authToken = UUID.randomUUID().toString()
        )
        registeredUserRepository.save(newRegisteredUser)
    }

    fun login(userCredentials: UserCredentialsDto): String {
        val registeredUser = findByEmail(userCredentials.email)
        if (!passwordEncoder.matches(userCredentials.password, registeredUser.password)) {
            throw IncorrectCredentialsException()
        }
        return registeredUser.authToken
    }

    private fun findByEmail(email: String): RegisteredUser = registeredUserRepository.findByEmail(email).orElseThrow { UserNotFoundException() }
}

