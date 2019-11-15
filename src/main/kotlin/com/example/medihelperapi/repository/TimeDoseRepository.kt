package com.example.medihelperapi.repository

import com.example.medihelperapi.model.MedicinePlan
import com.example.medihelperapi.model.TimeDose
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TimeDoseRepository : JpaRepository<TimeDose, Long> {

    fun deleteAllByMedicinePlan(medicinePlan: MedicinePlan)
}