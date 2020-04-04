package com.example.medihelperapi.service

import com.example.medihelperapi.dto.GetMedicineDto
import com.example.medihelperapi.dto.PostMedicineDto
import com.example.medihelperapi.repository.MedicinesRepo
import org.springframework.stereotype.Service

@Service
class MedicinesService(
    private val medicinesRepo: MedicinesRepo,
    private val userService: UserService
) {

    fun addNew(dto: PostMedicineDto) {
        val parent = userService.expectParent()
        val newMedicine = dto.toEntity(parent)
        medicinesRepo.save(newMedicine)
    }

    fun getAll(): List<GetMedicineDto> {
        val parent = userService.expectParent()
        return medicinesRepo.findAllByParent(parent).map {
            GetMedicineDto(it)
        }
    }

    fun delete(id: Long) {
        medicinesRepo.deleteById(id)
    }
}