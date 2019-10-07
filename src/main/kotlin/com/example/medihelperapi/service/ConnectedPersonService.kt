package com.example.medihelperapi.service

import com.example.medihelperapi.dto.MedicineDto
import com.example.medihelperapi.dto.MedicinePlanDto
import com.example.medihelperapi.dto.PlannedMedicineDto
import com.example.medihelperapi.model.Person
import com.example.medihelperapi.repository.MedicinePlanRepository
import com.example.medihelperapi.repository.MedicineRepository
import com.example.medihelperapi.repository.PersonRepository
import com.example.medihelperapi.repository.PlannedMedicineRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service

@Service
class ConnectedPersonService(
        private val personRepository: PersonRepository,
        private val medicineRepository: MedicineRepository,
        private val medicinePlanRepository: MedicinePlanRepository,
        private val plannedMedicineRepository: PlannedMedicineRepository
) {
    private val currPerson: Person
        get() {
            val user = SecurityContextHolder.getContext().authentication.principal as User
            return personRepository.findById(user.username.toLong()).orElseThrow { UserNotFoundException() }
        }

    fun getMedicines(): List<MedicineDto> {
        return medicineRepository.findAllByPersonId(currPerson.personId).map { MedicineDto(it, null) }
    }

    fun getMedicinesPlans(): List<MedicinePlanDto> {
        return medicinePlanRepository.findAllByPerson(currPerson).map { MedicinePlanDto(it, null) }
    }

    fun synchronizePlannedMedicines(updateDtoList: List<PlannedMedicineDto>): List<PlannedMedicineDto> {
        updateDtoList.forEach { plannedMedicineDto ->
            if (plannedMedicineRepository.existsById(plannedMedicineDto.plannedMedicineRemoteId!!)) {
                val updatedPlannedMedicine = plannedMedicineDto.toEntity(medicinePlanRepository)
                plannedMedicineRepository.save(updatedPlannedMedicine)
            }
        }

        return plannedMedicineRepository.findAllByMedicinePlanPerson(currPerson).map { PlannedMedicineDto(it, null) }
    }
}