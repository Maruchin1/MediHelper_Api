package com.example.medihelperapi.model

import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "times_doses")
data class TimeDose(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val timeOfTakingId: Long = 0,

        @ManyToOne
        var medicinePlan: MedicinePlan,

        var doseSize: Float,

        var time: LocalTime
)