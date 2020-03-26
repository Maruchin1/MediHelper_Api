package com.example.medihelperapi.service

import com.example.medihelperapi.dto.PostChildMedicineDto
import com.example.medihelperapi.model.ChildMedicine
import com.example.medihelperapi.repository.ChildMedicinesRepo
import com.example.medihelperapi.repository.ChildrenRepo
import com.example.medihelperapi.repository.MedicinesRepo
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ChildMedicineService(
    private val childMedicinesRepo: ChildMedicinesRepo,
    private val childrenRepo: ChildrenRepo,
    private val medicinesRepo: MedicinesRepo
    ) {

    fun assignMedicineToChild(dto: PostChildMedicineDto) {
        val child = childrenRepo.findByIdOrNull(dto.childId) ?: throw ChildNotFound()
        val medicine = medicinesRepo.findByIdOrNull(dto.medicineId) ?: throw MedicineNotFound()
        val newChildMedicine = ChildMedicine(child = child, medicine = medicine)
        childMedicinesRepo.save(newChildMedicine)
    }

    fun delete(id: Long) {
        childMedicinesRepo.deleteById(id)
    }
}