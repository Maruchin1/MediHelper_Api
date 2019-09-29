package com.example.medihelperapi.service

import com.example.medihelperapi.dto.MedicinePlanDto
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
    fun synchronizeMedicinesPlans(
            registeredUser: RegisteredUser,
            insertUpdateDtoList: List<MedicinePlanDto>,
            deleteRemoteIdList: List<Long>
    ): List<MedicinePlanDto> {
        val insertDtoList = insertUpdateDtoList.filter { it.medicinePlanRemoteId == null }
        val updateDtoList = insertUpdateDtoList.filter { it.medicinePlanRemoteId != null }

        val localIdRemoteIdPairList = mutableListOf<Pair<Int, Long>>()
        insertDtoList.forEach { medicinePlanDto ->
            val newMedicinePlan = medicinePlanDto.toMedicinePlanEntity(medicineRepository, personRepository)
            val savedMedicinePlan = medicinePlanRepository.save(newMedicinePlan)
            localIdRemoteIdPairList.add(Pair(medicinePlanDto.medicinePlanLocalId!!, savedMedicinePlan.medicinePlanId))
        }
        updateDtoList.forEach { medicinePlanDto ->
            if (medicinePlanRepository.existsById(medicinePlanDto.medicinePlanRemoteId!!)) {
                val updatedMedicinePlan = medicinePlanDto.toMedicinePlanEntity(medicineRepository, personRepository)
                medicinePlanRepository.save(updatedMedicinePlan)
            }
        }
        deleteRemoteIdList.forEach { medicinePlanId ->
            if (medicinePlanRepository.existsById(medicinePlanId)) {
                medicinePlanRepository.deleteById(medicinePlanId)
            }
        }

        return medicinePlanRepository.findAllByMedicineRegisteredUser(registeredUser).map { medicinePlan ->
            val medicinePlanLocalId = localIdRemoteIdPairList.find { it.second == medicinePlan.medicinePlanId }?.first
            MedicinePlanDto(medicinePlan, medicinePlanLocalId)
        }
    }
}