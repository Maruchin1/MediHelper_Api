package com.example.medihelperapi.dto

import com.example.medihelperapi.model.Medicine
import com.example.medihelperapi.model.RegisteredUser
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.time.LocalDateTime

data class MedicinePostDto(
        @JsonProperty(value = "medicineLocalId")
        val medicineLocalId: Int,

        @JsonProperty(value = "medicineName")
        val medicineName: String,

        @JsonProperty(value = "medicineUnit")
        val medicineUnit: String,

        @JsonProperty(value = "expireDate")
        @JsonFormat(pattern = "dd-MM-yyyy")
        val expireDate: LocalDate?,

        @JsonProperty(value = "packageSize")
        val packageSize: Float?,

        @JsonProperty(value = "currState")
        val currState: Float?,

        @JsonProperty(value = "additionalInfo")
        val additionalInfo: String?,

        @JsonProperty(value = "operationTime")
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        val operationTime: LocalDateTime
) {
        fun toMedicineEntity(registeredUser: RegisteredUser) = Medicine(
                registeredUser = registeredUser,
                medicineName = medicineName,
                medicineUnit = medicineUnit,
                expireDate = expireDate,
                packageSize = packageSize,
                currState = currState,
                additionalInfo = additionalInfo,
                lastModificationTime = operationTime
        )
}