package com.example.medihelperapi.model

import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "times_of_taking")
data class TimeDose(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val timeOfTakingId: Long = 0,

        var doseSize: Float,

        var time: LocalTime
)