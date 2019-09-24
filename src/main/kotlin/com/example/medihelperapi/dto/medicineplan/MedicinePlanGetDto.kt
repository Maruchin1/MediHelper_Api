package com.example.medihelperapi.dto.medicineplan

import com.example.medihelperapi.model.MedicinePlan
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class MedicinePlanGetDto(
        @JsonProperty(value = "medicinePlanRemoteId")
        val medicinePlanRemoteId: Long,

        @JsonProperty(value = "medicineRemoteId")
        val medicineRemoteId: Long,

        @JsonProperty(value = "personRemoteId")
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
    constructor(medicinePlan: MedicinePlan) : this(
            medicinePlanRemoteId = medicinePlan.medicinePlanId,
            medicineRemoteId = medicinePlan.medicine.medicineId,
            personRemoteId = medicinePlan.person.personId,
            startDate = medicinePlan.startDate,
            endDate = medicinePlan.endDate,
            durationType = medicinePlan.durationType,
            daysOfWeek = medicinePlan.daysOfWeek,
            intervalOfDays = medicinePlan.intervalOfDays,
            daysType = medicinePlan.daysType,
            timeOfTakingList = medicinePlan.timeOfTakingSet.map { TimeOfTakingDto(it) }
    )
}