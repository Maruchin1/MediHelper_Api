package com.example.medihelperapi.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class SyncRequestDto(
        @JsonProperty(value = "insertUpdateMedicineDtoList")
        val insertUpdateMedicineDtoList: List<MedicineDto>,

        @JsonProperty(value = "insertUpdatePersonDtoList")
        val insertUpdatePersonDtoList: List<PersonDto>,

        @JsonProperty(value = "insertUpdateMedicinePlanDtoList")
        val insertUpdateMedicinePlanDtoList: List<MedicinePlanDto>,

        @JsonProperty(value = "deleteMedicineRemoteIdList")
        val deleteMedicineRemoteIdList: List<Long>,

        @JsonProperty(value = "deletePersonRemoteIdList")
        val deletePersonRemoteIdList: List<Long>,

        @JsonProperty(value = "deleteMedicinePlanRemoteIdList")
        val deleteMedicinePlanRemoteIdList: List<Long>
)