package com.example.medihelperapi.dto

import com.example.medihelperapi.model.PlannedMedicine
import com.example.medihelperapi.repository.MedicinePlanRepository
import com.example.medihelperapi.service.MedicinePlanNotFoundException
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.time.LocalTime

data class PlannedMedicineDto(
        @JsonProperty(value = "plannedMedicineLocalId")
        val plannedMedicineLocalId: Int?,

        @JsonProperty(value = "plannedMedicineRemoteId")
        val plannedMedicineRemoteId: Long?,

        @JsonProperty(value = "medicinePlanRemoteId")
        val medicinePlanRemoteId: Long,

        @JsonProperty(value = "plannedDate")
        @JsonFormat(pattern = "dd-MM-yyyy")
        val plannedDate: LocalDate,

        @JsonProperty(value = "plannedTime")
        @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
        val plannedTime: LocalTime,

        @JsonProperty(value = "plannedDoseSize")
        val plannedDoseSize: Float,

        @JsonProperty(value = "statusOfTaking")
        val statusOfTaking: String
) {
    constructor(plannedMedicine: PlannedMedicine, plannedMedicineLocalId: Int?) : this(
            plannedMedicineLocalId = plannedMedicineLocalId,
            plannedMedicineRemoteId = plannedMedicine.plannedMedicineId,
            medicinePlanRemoteId = plannedMedicine.medicinePlan.medicinePlanId,
            plannedDate = plannedMedicine.plannedDate,
            plannedTime = plannedMedicine.plannedTime,
            plannedDoseSize = plannedMedicine.plannedDoseSize,
            statusOfTaking = plannedMedicine.statusOfTaking
    )

    fun toEntity(medicinePlanRepository: MedicinePlanRepository) = PlannedMedicine(
            plannedMedicineId = plannedMedicineRemoteId ?: 0,
            medicinePlan = medicinePlanRepository.findById(medicinePlanRemoteId).orElseThrow { MedicinePlanNotFoundException() },
            plannedDate = plannedDate,
            plannedTime = plannedTime,
            plannedDoseSize = plannedDoseSize,
            statusOfTaking = statusOfTaking
    )
}