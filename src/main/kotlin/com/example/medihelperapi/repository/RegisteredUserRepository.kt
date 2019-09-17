package com.example.medihelperapi.repository

import com.example.medihelperapi.model.RegisteredUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RegisteredUserRepository : JpaRepository<RegisteredUser, Long> {

    fun findByAuthToken(token: String): Optional<RegisteredUser>

    fun findByEmail(email: String): Optional<RegisteredUser>
}