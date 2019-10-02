package com.example.medihelperapi.repository

import com.example.medihelperapi.model.Person
import com.example.medihelperapi.model.RegisteredUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PersonRepository : JpaRepository<Person, Long> {
    fun findAllByRegisteredUser(registeredUser: RegisteredUser): List<Person>

    fun findByConnectionKey(connectionKey: String): Optional<Person>

    fun findByAuthToken(authToken: String): Optional<Person>

    fun existsByConnectionKey(connectionKey: String): Boolean

    fun deleteAllByRegisteredUser(registeredUser: RegisteredUser)

    fun countByRegisteredUser(registeredUser: RegisteredUser): Long
}