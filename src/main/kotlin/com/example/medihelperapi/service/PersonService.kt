package com.example.medihelperapi.service

import com.example.medihelperapi.dto.PersonDto
import com.example.medihelperapi.model.RegisteredUser
import com.example.medihelperapi.repository.PersonRepository
import org.springframework.stereotype.Service

@Service
class PersonService(private val personRepository: PersonRepository) {
//    fun overwritePersons(registeredUser: RegisteredUser, postDtoList: List<PersonPostDto>): List<PostResponseDto> {
//        personRepository.deleteAllByRegisteredUser(registeredUser)
//        val postResponseDtoList = mutableListOf<PostResponseDto>()
//        postDtoList.forEach { personPostDto ->
//            val newPerson = personPostDto.toPersonEntity(registeredUser)
//            val savedPerson = personRepository.save(newPerson)
//            postResponseDtoList.add(PostResponseDto(localId = personPostDto.personLocalId, remoteId = savedPerson.personId))
//        }
//        return postResponseDtoList
//    }
//
//    fun getAllPersons(registeredUser: RegisteredUser): List<PersonGetDto> {
//        val allPersonList = personRepository.findAllByRegisteredUser(registeredUser)
//        return allPersonList.map { person -> PersonGetDto(person) }
//    }

    fun synchronizePersons(
            registeredUser: RegisteredUser,
            insertUpdateDtoList: List<PersonDto>,
            deleteRemoteIdList: List<Long>
    ): List<PersonDto> {
        val insertDtoList = insertUpdateDtoList.filter { it.personRemoteId == null }
        val updateDtoList = insertUpdateDtoList.filter { it.personRemoteId != null }

        val localIdRemoteIdPairList = mutableListOf<Pair<Int, Long>>()
        insertDtoList.forEach { personDto ->
            val newPerson = personDto.toPersonEntity(registeredUser)
            val savedPerson = personRepository.save(newPerson)
            localIdRemoteIdPairList.add(Pair(personDto.personLocalId!!, savedPerson.personId))
        }
        updateDtoList.forEach { personDto ->
            if (personRepository.existsById(personDto.personRemoteId!!)) {
                val updatedPerson = personDto.toPersonEntity(registeredUser)
                personRepository.save(updatedPerson)
            }
        }
        deleteRemoteIdList.forEach { personId ->
            if (personRepository.existsById(personId)) {
                personRepository.deleteById(personId)
            }
        }

        return personRepository.findAllByRegisteredUser(registeredUser).map { person ->
            val personLocalId = localIdRemoteIdPairList.find { it.second == person.personId }?.first
            PersonDto(person, personLocalId)
        }
    }
}