package com.example.medihelperapi.service

import com.example.medihelperapi.dto.ConnectedPersonDto
import com.example.medihelperapi.dto.LoginInputDto
import com.example.medihelperapi.dto.LoginResponseDto
import com.example.medihelperapi.dto.RegisterInputDto
import com.example.medihelperapi.model.RegisteredUser
import com.example.medihelperapi.repository.MedicineRepository
import com.example.medihelperapi.repository.PersonRepository
import com.example.medihelperapi.repository.RegisteredUserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
        private val registeredUserRepository: RegisteredUserRepository,
        private val medicineRepository: MedicineRepository,
        private val personRepository: PersonRepository,
        private val passwordEncoder: PasswordEncoder,
        private val mapper: EntityDtoMapper
) {
    fun register(registerInputDto: RegisterInputDto) {
        if (registeredUserRepository.existsByEmail(registerInputDto.email)) {
            throw RegisteredUserExistsException()
        }
        val newRegisteredUser = RegisteredUser(
                userName = registerInputDto.userName,
                email = registerInputDto.email,
                password = passwordEncoder.encode(registerInputDto.password),
                authToken = UUID.randomUUID().toString()
        )
        registeredUserRepository.save(newRegisteredUser)
    }

    fun login(loginInputDto: LoginInputDto): LoginResponseDto {
        val registeredUser = registeredUserRepository.findByEmail(loginInputDto.email)
                .orElseThrow { UserNotFoundException() }
        if (!passwordEncoder.matches(loginInputDto.password, registeredUser.password)) {
            throw IncorrectCredentialsException()
        }
        return LoginResponseDto(
                authToken = registeredUser.authToken,
                isDataAvailable = isDataAvailable(registeredUser)
        )
    }

    fun patronConnect(connectionKey: String): ConnectedPersonDto {
        val person = personRepository.findByConnectionKey(connectionKey).orElseThrow { PersonNotFoundException() }
        person.authToken = UUID.randomUUID().toString()
        val savedPerson = personRepository.save(person)
        return mapper.personToConnectedPersonDto(savedPerson)
    }

    private fun isDataAvailable(user: RegisteredUser): Boolean {
        return medicineRepository.countByRegisteredUser(user) > 0 ||
                personRepository.countByRegisteredUser(user) > 0
    }
}

