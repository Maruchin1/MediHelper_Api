package com.example.medihelperapi.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDate
import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "planned_medicines")
data class PlannedMedicine(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val plannedMedicineId: Long,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "medicinePlanId")
        @OnDelete(action = OnDeleteAction.CASCADE)
        val medicinePlan: MedicinePlan,

        var plannedDate: LocalDate,

        var plannedTime: LocalTime,

        var plannedDoseSize: Float,

        var statusOfTaking: String
)