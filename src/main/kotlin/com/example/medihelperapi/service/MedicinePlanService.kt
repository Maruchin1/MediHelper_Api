package com.example.medihelperapi.service

import com.example.medihelperapi.dto.medicineplan.MedicinePlanGetDto
import com.example.medihelperapi.dto.medicineplan.MedicinePlanPostDto
import com.example.medihelperapi.dto.PostResponseDto
import com.example.medihelperapi.model.RegisteredUser
import com.example.medihelperapi.repository.MedicinePlanRepository
import com.example.medihelperapi.repository.MedicineRepository
import com.example.medihelperapi.repository.PersonRepository
import org.springframework.stereotype.Service

@Service
class MedicinePlanService(
        private val medicinePlanRepository: MedicinePlanRepository,
        private val medicineRepository: MedicineRepository,
        private val personRepository: PersonRepository
) {
    fun overWriteMedicinesPlans(registeredUser: RegisteredUser, postDtoList: List<MedicinePlanPostDto>): List<PostResponseDto> {
        medicinePlanRepository.deleteAllByMedicineRegisteredUser(registeredUser)
        val postResponseDtoList = mutableListOf<PostResponseDto>()
        postDtoList.forEach { medicinePlanPostDto ->
            val newMedicinePlan = medicinePlanPostDto.toMedicinePlanEntity(
                    medicine = findMedicineById(medicinePlanPostDto.medicineRemoteId),
                    person = findPersonById(medicinePlanPostDto.personRemoteId)
            )
            val savedMedicinePlan = medicinePlanRepository.save(newMedicinePlan)
            postResponseDtoList.add(PostResponseDto(localId = medicinePlanPostDto.medicinePlanLocalId, remoteId = savedMedicinePlan.medicinePlanId))
        }
        return postResponseDtoList
    }

    fun getAllMedicinesPlans(registeredUser: RegisteredUser): List<MedicinePlanGetDto> {
        val allMedicinesPlans = medicinePlanRepository.findAllByMedicineRegisteredUser(registeredUser)
        return allMedicinesPlans.map { medicinePlan -> MedicinePlanGetDto(medicinePlan) }
    }

    private fun findMedicineById(medicineId: Long) = medicineRepository.findById(medicineId).orElseThrow {
        println("MedicineNotFound")
        MedicineNotFoundException()
    }

    private fun findPersonById(personId: Long) = personRepository.findById(personId).orElseThrow {
        println("PersonNotFound")
        PersonNotFoundException()
    }
}