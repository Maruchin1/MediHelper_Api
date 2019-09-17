package com.example.medihelperapi.repository

import com.example.medihelperapi.model.AppUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<AppUser, Long> {

    fun findByEmailAndPassword(email: String, password: String): Optional<AppUser>

    fun findByToken(token: String): Optional<AppUser>
}