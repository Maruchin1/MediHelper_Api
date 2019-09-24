package com.example.medihelperapi.service

import com.example.medihelperapi.dto.registereduser.NewPasswordDto
import com.example.medihelperapi.dto.registereduser.UserCredentialsDto
import com.example.medihelperapi.model.RegisteredUser
import com.example.medihelperapi.repository.*
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class RegisteredUserService(
        private val registeredUserRepository: RegisteredUserRepository,
        private val medicineRepository: MedicineRepository,
        private val personRepository: PersonRepository,
        private val passwordEncoder: PasswordEncoder
) {
    fun register(userCredentials: UserCredentialsDto) {
        if (registeredUserRepository.existsByEmail(userCredentials.email)) {
            throw RegisteredUserExistsException()
        }
        val newRegisteredUser = RegisteredUser(
                email = userCredentials.email,
                password = passwordEncoder.encode(userCredentials.password)
        )
        registeredUserRepository.save(newRegisteredUser)
    }

    fun login(userCredentials: UserCredentialsDto): String {
        val registeredUser = findByEmail(userCredentials.email)
        if (!passwordEncoder.matches(userCredentials.password, registeredUser.password)) {
            throw IncorrectCredentialsException()
        }
        val newAuthToken = UUID.randomUUID().toString()
        registeredUser.authToken = newAuthToken
        registeredUserRepository.save(registeredUser)
        return newAuthToken
    }

    fun changePassword(registeredUser: RegisteredUser, newPassword: NewPasswordDto) {
        registeredUser.password = passwordEncoder.encode(newPassword.value)
        registeredUserRepository.save(registeredUser)
    }

    fun hasData(registeredUser: RegisteredUser): Boolean {
        return medicineRepository.countByRegisteredUser(registeredUser) > 0 ||
                personRepository.countByRegisteredUser(registeredUser) > 0
    }

    fun getLoggedUser(): RegisteredUser {
        val user = SecurityContextHolder.getContext().authentication.principal as User
        return findByEmail(user.username)
    }

    private fun findByEmail(email: String): RegisteredUser = registeredUserRepository.findByEmail(email).orElseThrow { UserNotFoundException() }
}