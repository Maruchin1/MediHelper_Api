package com.example.medihelperapi.service

import com.example.medihelperapi.dto.PersonGetDto
import com.example.medihelperapi.dto.PersonPostDto
import com.example.medihelperapi.dto.PostResponseDto
import com.example.medihelperapi.model.RegisteredUser
import com.example.medihelperapi.repository.PersonRepository
import com.example.medihelperapi.repository.RegisteredUserRepository
import org.springframework.stereotype.Service

@Service
class PersonService (
        private val personRepository: PersonRepository,
        private val registeredUserRepository: RegisteredUserRepository
) {
    fun overwritePersons(email: String, personPostDtoList: List<PersonPostDto>): List<PostResponseDto> {
        personRepository.deleteAll()
        val registeredUser = findRegisteredUserByEmail(email)
        val postResponseDtoList = mutableListOf<PostResponseDto>()
        personPostDtoList.forEach { personPostDto ->
            val newPerson = personPostDto.toPersonEntity(registeredUser)
            val savedPerson = personRepository.save(newPerson)
            postResponseDtoList.add(PostResponseDto(localId = personPostDto.personLocalId, remoteId = savedPerson.personId))
        }
        return postResponseDtoList
    }

    fun getAllPersons(email: String): List<PersonGetDto> {
        val registeredUser = findRegisteredUserByEmail(email)
        val allPersonList = personRepository.findAllByRegisteredUser(registeredUser)
        return allPersonList.map { person -> PersonGetDto(person) }
    }

    private fun findRegisteredUserByEmail(email: String): RegisteredUser = registeredUserRepository.findByEmail(email)
            .orElseThrow { UserNotFoundException() }
}