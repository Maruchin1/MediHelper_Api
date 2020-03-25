package com.example.medihelperapi.dto

import com.example.medihelperapi.model.Medicine
import com.example.medihelperapi.model.Parent
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class PostMedicineDto(
    @JsonProperty(value = "medicineId")
    val medicineId: Long,

    @JsonProperty(value = "medicineName")
    val name: String,

    @JsonProperty(value = "medicineUnit")
    val unit: String,

    @JsonProperty(value = "expireDate")
    val expireDate: LocalDate?,

    @JsonProperty(value = "packageSize")
    val packageSize: Float?,

    @JsonProperty(value = "currState")
    val currState: Float?
) {
    fun toEntity(parent: Parent) = Medicine(
        parent = parent,
        name = name,
        unit = unit,
        expireDate = expireDate,
        packageSize = packageSize,
        currState = currState
    )
}