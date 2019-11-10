package com.example.medihelperapi.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalTime

data class TimeDoseDto(
        @JsonProperty(value = "doseSize")
        val doseSize: Float,

        @JsonProperty(value = "time")
        @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
        val time: LocalTime
)