package com.example.medihelperapi.service

import com.example.medihelperapi.repository.PersonRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class PersonService(private val personRepository: PersonRepository) {

    fun getAuthToken(connectionKey: String): String {
        val person = personRepository.findByConnectionKey(connectionKey).orElseThrow { PersonNotFoundException() }
        person.authToken = UUID.randomUUID().toString()
        val savedPerson = personRepository.save(person)
        return savedPerson.authToken
    }
}