package com.example.medihelperapi.model

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "medicines")
data class Medicine(
        @EmbeddedId
        val medicineID: MedicineId,

        var medicineUnit: String,

        var expireDate: LocalDate?,

        var packageSize: Float?,

        var currState: Float?,

        var additionalInfo: String?,

        var lastModificationTime: LocalDateTime
)