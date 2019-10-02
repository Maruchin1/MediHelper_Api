package com.example.medihelperapi.repository

import com.example.medihelperapi.model.Medicine
import com.example.medihelperapi.model.RegisteredUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface MedicineRepository : JpaRepository<Medicine, Long> {
    fun findAllByRegisteredUser(registeredUser: RegisteredUser): List<Medicine>

    @Query("select m from Medicine m join m.medicinePlanList mp join mp.person p where p.personId = :personId group by m.medicineId")
    fun findAllByPersonId(@Param("personId") personId: Long): List<Medicine>

    fun deleteAllByRegisteredUser(registeredUser: RegisteredUser)

    fun countByRegisteredUser(registeredUser: RegisteredUser): Long
}