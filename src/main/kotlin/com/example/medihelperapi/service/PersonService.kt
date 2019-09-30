package com.example.medihelperapi.service

import com.example.medihelperapi.repository.PersonRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class PersonService(private val personRepository: PersonRepository) {

    fun getAuthToken(personTempKey: String): String {
        val person = personRepository.findByTempKey(personTempKey).orElseThrow { PersonNotFoundException() }
        person.authToken = UUID.randomUUID().toString()
        val savedPerson = personRepository.save(person)
        return savedPerson.authToken
    }
}