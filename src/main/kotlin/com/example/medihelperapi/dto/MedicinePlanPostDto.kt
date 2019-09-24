package com.example.medihelperapi.dto

import com.example.medihelperapi.model.*
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class MedicinePlanPostDto(
        @JsonProperty(value = "medicinePlanLocalId")
        val medicinePlanLocalId: Int,

        @JsonProperty(value = "medicineId")
        val medicineRemoteId: Long,

        @JsonProperty(value = "personId")
        val personRemoteId: Long,

        @JsonProperty(value = "startDate")
        @JsonFormat(pattern = "dd-MM-yyyy")
        val startDate: LocalDate,

        @JsonProperty(value = "endDate")
        @JsonFormat(pattern = "dd-MM-yyyy")
        val endDate: LocalDate?,

        @JsonProperty(value = "durationType")
        val durationType: String,

        @JsonProperty(value = "daysOfWeek")
        val daysOfWeek: MedicinePlan.DaysOfWeek?,

        @JsonProperty(value = "intervalOfDays")
        val intervalOfDays: Int?,

        @JsonProperty(value = "daysType")
        val daysType: String,

        @JsonProperty(value = "timeOfTakingList")
        val timeOfTakingList: List<TimeOfTakingDto>
) {
    fun toMedicinePlanEntity(medicine: Medicine, person: Person): MedicinePlan {
        val medicinePlan = MedicinePlan(
                medicine = medicine,
                person = person,
                startDate = startDate,
                endDate = endDate,
                durationType = durationType,
                daysOfWeek = daysOfWeek,
                intervalOfDays = intervalOfDays,
                daysType = daysType,
                timeOfTakingSet = timeOfTakingList.map { it.toTimeOfTakingEntity() }.toSet()
        )
//        medicinePlan.timeOfTakingSet = timeOfTakingList.map { it.toTimeOfTakingEntity(medicinePlan) }.toSet()
        return medicinePlan
    }
}