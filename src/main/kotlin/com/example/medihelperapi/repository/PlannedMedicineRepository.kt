package com.example.medihelperapi.repository

import com.example.medihelperapi.model.Person
import com.example.medihelperapi.model.PlannedMedicine
import com.example.medihelperapi.model.RegisteredUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlannedMedicineRepository : JpaRepository<PlannedMedicine, Long> {

    fun findAllByMedicinePlanMedicineRegisteredUser(registeredUser: RegisteredUser): List<PlannedMedicine>

    fun findAllByMedicinePlanPerson(person: Person): List<PlannedMedicine>
}