package com.example.medihelperapi.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.time.LocalDateTime

data class MedicineDto(
     val medicineName: String,
     val medicineUnit: String,
     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
     val expireDate: LocalDate?,
     val packageSize: Float?,
     val currState: Float?,
     val additionalInfo: String?,
     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
     val operationTime: LocalDateTime
)