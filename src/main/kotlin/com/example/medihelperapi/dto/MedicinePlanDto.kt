package com.example.medihelperapi.dto

import com.example.medihelperapi.model.MedicinePlan
import com.example.medihelperapi.repository.MedicineRepository
import com.example.medihelperapi.repository.PersonRepository
import com.example.medihelperapi.service.MedicineNotFoundException
import com.example.medihelperapi.service.PersonNotFoundException
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

        @JsonProperty(value = "timeOfTakingDtoList")
        val timeOfTakingDtoList: List<TimeOfTakingDto>
) {
    constructor(medicinePlan: MedicinePlan, medicinePlanLocalId: Int?) : this(
            medicinePlanLocalId = medicinePlanLocalId,
            medicinePlanRemoteId = medicinePlan.medicinePlanId,
            medicineRemoteId = medicinePlan.medicine.medicineId,
            personRemoteId = medicinePlan.person.personId,
            startDate = medicinePlan.startDate,
            endDate = medicinePlan.endDate,
            durationType = medicinePlan.durationType,
            daysOfWeek = medicinePlan.daysOfWeek,
            intervalOfDays = medicinePlan.intervalOfDays,
            daysType = medicinePlan.daysType,
            timeOfTakingDtoList = medicinePlan.timeOfTakingList.map { TimeOfTakingDto(it) }
    )

    fun toMedicinePlanEntity(medicineRepository: MedicineRepository, personRepository: PersonRepository) = MedicinePlan(
            medicinePlanId = medicinePlanRemoteId ?: 0,
            medicine = medicineRepository.findById(medicineRemoteId).orElseThrow { MedicineNotFoundException() },
            person = personRepository.findById(personRemoteId).orElseThrow { PersonNotFoundException() },
            startDate = startDate,
            endDate = endDate,
            durationType = durationType,
            daysOfWeek = daysOfWeek,
            intervalOfDays = intervalOfDays,
            daysType = daysType,
            timeOfTakingList = timeOfTakingDtoList.map { it.toTimeOfTakingEntity() }
    )
}