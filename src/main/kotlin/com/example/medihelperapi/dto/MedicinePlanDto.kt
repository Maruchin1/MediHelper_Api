package com.example.medihelperapi.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class MedicinePlanDto(
        @JsonProperty(value = "medicinePlanLocalId")
        val medicinePlanLocalId: Int?,

        @JsonProperty(value = "medicinePlanRemoteId")
        val medicinePlanRemoteId: Long?,

        @JsonProperty(value = "medicineRemoteId")
        val medicineRemoteId: Long,

        @JsonProperty(value = "personRemoteId")
        val personRemoteId: Long?,

        @JsonProperty(value = "startDate")
        @JsonFormat(pattern = "dd-MM-yyyy")
        val startDate: LocalDate,

        @JsonProperty(value = "endDate")
        @JsonFormat(pattern = "dd-MM-yyyy")
        val endDate: LocalDate?,

        @JsonProperty(value = "durationType")
        val durationType: String,

        @JsonProperty(value = "daysOfWeek")
        val daysOfWeekDto: DaysOfWeekDto?,

        @JsonProperty(value = "intervalOfDays")
        val intervalOfDays: Int?,

        @JsonProperty(value = "daysType")
        val daysType: String?,

        @JsonProperty(value = "timeOfTakingDtoList")
        val timeDoseDtoList: List<TimeDoseDto>
)
