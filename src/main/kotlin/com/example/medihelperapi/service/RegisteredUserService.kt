package com.example.medihelperapi.service

import com.example.medihelperapi.dto.UserRegistrationDto
import com.example.medihelperapi.model.RegisteredUser
import com.example.medihelperapi.repository.RegisteredUserRepository
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class RegisteredUserService(
        private val registeredUserRepository: RegisteredUserRepository,
        private val passwordEncoder: PasswordEncoder
) {

    fun findByEmail(email: String) = registeredUserRepository.findByEmail(email).orElseThrow { UserNotFoundException() }

    fun login(email: String, password: String): String {
        val registeredUser = findByEmail(email)
        if (!passwordEncoder.matches(password, registeredUser.password)) {
            throw IncorrectCredentialsException()
        }
        val newAuthToken = UUID.randomUUID().toString()
        registeredUser.authToken = newAuthToken
        registeredUserRepository.save(registeredUser)
        return newAuthToken
    }

    fun register(registrationDto: UserRegistrationDto) {
        // todo rozważyć tutaj więcej walidacji
        if (registrationDto.password != registrationDto.passwordConfirmation) {
            throw PasswordConfirmationIncorrectException()
        }
        val newRegisteredUser = RegisteredUser(
                email = registrationDto.email,
                password = passwordEncoder.encode(registrationDto.password)
        )
        registeredUserRepository.save(newRegisteredUser)
    }
}