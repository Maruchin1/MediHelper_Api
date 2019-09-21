package com.example.medihelperapi.dto

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.time.LocalDateTime

data class MedicinePutDto(
        @JsonProperty(value = "medicineName")
        val medicineName: String,

        @JsonProperty(value = "medicineUnit")
        val medicineUnit: String,

        @JsonProperty(value = "expireDate")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        val expireDate: LocalDate?,

        @JsonProperty(value = "packageSize")
        val packageSize: Float?,

        @JsonProperty(value = "currState")
        val currState: Float?,

        @JsonProperty(value = "additionalInfo")
        val additionalInfo: String?,

        @JsonProperty(value = "operationTime")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        val operationTime: LocalDateTime
)