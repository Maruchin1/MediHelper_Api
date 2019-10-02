package com.example.medihelperapi.service

import com.example.medihelperapi.dto.PersonProfileDataDto
import com.example.medihelperapi.repository.PersonRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class PersonService(private val personRepository: PersonRepository) {

    fun getProfileData(connectionKey: String): PersonProfileDataDto {
        val person = personRepository.findByConnectionKey(connectionKey).orElseThrow { PersonNotFoundException() }
        person.authToken = UUID.randomUUID().toString()
        val savedPerson = personRepository.save(person)
        return PersonProfileDataDto(savedPerson)
    }
}