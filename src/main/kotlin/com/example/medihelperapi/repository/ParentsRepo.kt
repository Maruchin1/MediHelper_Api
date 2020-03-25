package com.example.medihelperapi.repository

import com.example.medihelperapi.model.Parent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ParentsRepo : JpaRepository<Parent, Long> {

    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): Optional<Parent>

    fun findByAuthToken(authToken: String): Optional<Parent>
}