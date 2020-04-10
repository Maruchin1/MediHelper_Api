package com.example.medihelperapi.service

import com.example.medihelperapi.dto.GetMedicineDto
import com.example.medihelperapi.dto.PostMedicineDto
import com.example.medihelperapi.model.Medicine
import com.example.medihelperapi.repository.MedicinesRepo
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.Query


@Service
class MedicinesService(
    private val medicinesRepo: MedicinesRepo,
    private val userService: UserService,
    private val entityManager: EntityManager
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

    fun getByNameSafe(name: String): List<GetMedicineDto> {
        val parent = userService.expectParent()
        return medicinesRepo.findAllByParent(parent).filter {
            medicine -> medicine.name.equals(name)
        }.map {
            GetMedicineDto(it)
        }
    }

    fun getByNameUnsafe(name: String): List<GetMedicineDto> {
        val parentId = userService.expectParent().parentId
        val sql = "SELECT * FROM medicines WHERE name = '$name' AND parent_id = $parentId"

        val qu: Query = entityManager.createNativeQuery(sql, Medicine::class.java)
        val result: List<Medicine> = qu.resultList as List<Medicine>

        return result.map {
            GetMedicineDto(it)
        }
    }
}