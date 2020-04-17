package com.example.medihelperapi.dto

import com.example.medihelperapi.model.Child
import com.example.medihelperapi.model.ChildMedicine
import com.fasterxml.jackson.annotation.JsonProperty

class GetChildMedicineDto (

        @JsonProperty(value = "assignedMedicineId")
        val assignedMedicineId: Long,

        @JsonProperty(value = "medicine")
        val medicine: GetMedicineDto
)  {
    constructor(entity: ChildMedicine) : this(
            assignedMedicineId = entity.assignedMedicineId,
            medicine = GetMedicineDto(entity.medicine)
    )
}