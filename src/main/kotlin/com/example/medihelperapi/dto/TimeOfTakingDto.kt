package com.example.medihelperapi.dto

import com.example.medihelperapi.model.MedicinePlan
import com.example.medihelperapi.model.TimeOfTaking
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalTime

data class TimeOfTakingDto(
        @JsonProperty(value = "doseSize")
        val doseSize: Float,

        @JsonProperty(value = "time")
        @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
        val time: LocalTime
) {
        constructor(timeOfTaking: TimeOfTaking) : this(
                doseSize = timeOfTaking.doseSize,
                time = timeOfTaking.time
        )

        fun toTimeOfTakingEntity() = TimeOfTaking(
                doseSize = doseSize,
                time = time
        )
}