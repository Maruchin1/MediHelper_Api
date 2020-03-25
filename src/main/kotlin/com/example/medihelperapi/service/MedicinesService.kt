package com.example.medihelperapi.service

import com.example.medihelperapi.dto.GetMedicineDto
import com.example.medihelperapi.dto.PostMedicineDto
import com.example.medihelperapi.repository.MedicinesRepo
import org.springframework.stereotype.Service

@Service
class MedicinesService(
    private val medicinesRepo: MedicinesRepo,
    private val currUserService: CurrUserService
) {

    fun addNew(dto: PostMedicineDto) {
        val parent = currUserService.expectParent()
        val newMedicine = dto.toEntity(parent)
        medicinesRepo.save(newMedicine)
    }

    fun getAll(): List<GetMedicineDto> {
        val parent = currUserService.expectParent()
        return medicinesRepo.findAllByParent(parent).map {
            GetMedicineDto(it)
        }
    }
}