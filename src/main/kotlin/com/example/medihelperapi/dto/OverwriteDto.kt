package com.example.medihelperapi.dto

data class OverwriteDto(
        val personPostDtoList: List<PersonPostDto>,
        val medicinePostDtoList: List<MedicinePostDto>,
        val medicinePlanPostDtoList: List<MedicinePlanPostDto>
)