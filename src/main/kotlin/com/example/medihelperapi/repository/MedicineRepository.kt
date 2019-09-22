package com.example.medihelperapi.repository

import com.example.medihelperapi.model.Medicine
import com.example.medihelperapi.model.RegisteredUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MedicineRepository : JpaRepository<Medicine, Long> {
    fun findAllByRegisteredUser(registeredUser: RegisteredUser): List<Medicine>

    fun countByRegisteredUser(registeredUser: RegisteredUser): Long
}