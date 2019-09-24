package com.example.medihelperapi.service

import com.example.medihelperapi.dto.person.PersonGetDto
import com.example.medihelperapi.dto.person.PersonPostDto
import com.example.medihelperapi.dto.PostResponseDto
import com.example.medihelperapi.model.RegisteredUser
import com.example.medihelperapi.repository.PersonRepository
import org.springframework.stereotype.Service

@Service
class PersonService (private val personRepository: PersonRepository) {
    fun overwritePersons(registeredUser: RegisteredUser, personPostDtoList: List<PersonPostDto>): List<PostResponseDto> {
        personRepository.deleteAll()
        val postResponseDtoList = mutableListOf<PostResponseDto>()
        personPostDtoList.forEach { personPostDto ->
            val newPerson = personPostDto.toPersonEntity(registeredUser)
            val savedPerson = personRepository.save(newPerson)
            postResponseDtoList.add(PostResponseDto(localId = personPostDto.personLocalId, remoteId = savedPerson.personId))
        }
        return postResponseDtoList
    }

    fun getAllPersons(registeredUser: RegisteredUser): List<PersonGetDto> {
        val allPersonList = personRepository.findAllByRegisteredUser(registeredUser)
        return allPersonList.map { person -> PersonGetDto(person) }
    }
}