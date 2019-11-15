package com.example.medihelperapi.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class MedicineDto(
        @JsonProperty(value = "medicineLocalId")
        val medicineLocalId: Int?,

        @JsonProperty(value = "medicineRemoteId")
        val medicineRemoteId: Long?,

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

        @JsonProperty(value = "imageName")
        val imageName: String?
)