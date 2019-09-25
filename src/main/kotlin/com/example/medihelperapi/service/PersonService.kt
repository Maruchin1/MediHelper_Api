package com.example.medihelperapi.service

import com.example.medihelperapi.dto.PersonDto
import com.example.medihelperapi.dto.person.PersonGetDto
import com.example.medihelperapi.dto.person.PersonPostDto
import com.example.medihelperapi.dto.PostResponseDto
import com.example.medihelperapi.dto.SyncRequestDto
import com.example.medihelperapi.model.RegisteredUser
import com.example.medihelperapi.repository.PersonRepository
import org.springframework.stereotype.Service

@Service
class PersonService(private val personRepository: PersonRepository) {
    fun overwritePersons(registeredUser: RegisteredUser, postDtoList: List<PersonPostDto>): List<PostResponseDto> {
        personRepository.deleteAllByRegisteredUser(registeredUser)
        val postResponseDtoList = mutableListOf<PostResponseDto>()
        postDtoList.forEach { personPostDto ->
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

    fun synchronizePersons(registeredUser: RegisteredUser, syncRequestDto: SyncRequestDto<PersonDto>): List<PersonDto> {
        val insertDtoList = syncRequestDto.insertUpdateDtoList.filter { it.personRemoteId == null }
        val updateDtoList = syncRequestDto.insertUpdateDtoList.filter { it.personRemoteId != null }
        val deleteIdList = syncRequestDto.deleteRemoteIdList

        insertDtoList.forEach { personDto ->
            val newPerson = personDto.toPersonEntity(registeredUser)
            personRepository.save(newPerson)
        }
        updateDtoList.forEach { personDto ->
            if (personRepository.existsById(personDto.personRemoteId!!)) {
                val updatedPerson = personDto.toPersonEntity(registeredUser)
                personRepository.save(updatedPerson)
            }
        }
        deleteIdList.forEach { personId ->
            if (personRepository.existsById(personId)) {
                personRepository.deleteById(personId)
            }
        }

        return personRepository.findAllByRegisteredUser(registeredUser).map { PersonDto(it) }
    }
}