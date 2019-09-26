package com.example.medihelperapi.model

import java.time.LocalDate
import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "planned_medicines")
data class PlannedMedicine(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val plannedMedicineId: Long,

        var plannedDate: LocalDate,

        var plannedTime: LocalTime,

        var plannedDoseSize: Float,

        var statusOfTaking: String
)