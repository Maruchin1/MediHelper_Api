package com.example.medihelperapi.repository

import com.example.medihelperapi.model.DaysOfWeek
import com.example.medihelperapi.model.MedicinePlan
import org.springframework.data.jpa.repository.JpaRepository

interface DaysOfWeekRepository : JpaRepository<DaysOfWeek, Long> {

    fun deleteAllByMedicinePlan(medicinePlan: MedicinePlan)
}