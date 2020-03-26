package com.example.medihelperapi.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class PostChildMedicineDto(
    @JsonProperty(value = "childId")
    val childId: Long,

    @JsonProperty(value = "medicineId")
    val medicineId: Long
)