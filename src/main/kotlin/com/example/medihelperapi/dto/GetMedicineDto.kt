package com.example.medihelperapi.dto

import com.example.medihelperapi.model.Medicine
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class GetMedicineDto(
    @JsonProperty(value = "medicineId")
    val medicineId: Long,

    @JsonProperty(value = "name")
    val name: String,

    @JsonProperty(value = "unit")
    val unit: String,

    @JsonProperty(value = "expireDate")
    val expireDate: LocalDate?,

    @JsonProperty(value = "packageSize")
    val packageSize: Float?,

    @JsonProperty(value = "currState")
    val currState: Float?
) {
    constructor(entity: Medicine) : this(
        medicineId = entity.medicineId,
        name = entity.name,
        unit = entity.unit,
        expireDate = entity.expireDate,
        packageSize = entity.packageSize,
        currState = entity.currState
    )
}