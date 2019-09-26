package com.example.medihelperapi.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class SyncResponseDto(
        @JsonProperty(value = "medicineDtoList")
        val medicineDtoList: List<MedicineDto>,

        @JsonProperty(value = "personDtoList")
        val personDtoList: List<PersonDto>,

        @JsonProperty(value = "medicinePlanDto")
        val medicinePlanDtoList: List<MedicinePlanDto>
)