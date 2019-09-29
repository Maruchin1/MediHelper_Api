package com.example.medihelperapi.repository

import com.example.medihelperapi.model.MedicinePlan
import com.example.medihelperapi.model.RegisteredUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MedicinePlanRepository : JpaRepository<MedicinePlan, Long> {
    fun findAllByMedicineRegisteredUser(registeredUser: RegisteredUser): List<MedicinePlan>
}