package com.example.medihelperapi.service

import com.example.medihelperapi.dto.PostResponseDto
import com.example.medihelperapi.dto.plannedmedicine.PlannedMedicineGetDto
import com.example.medihelperapi.dto.plannedmedicine.PlannedMedicinePostDto
import com.example.medihelperapi.model.RegisteredUser
import com.example.medihelperapi.repository.MedicinePlanRepository
import com.example.medihelperapi.repository.PlannedMedicineRepository
import org.springframework.stereotype.Service

@Service
class PlannedMedicineService(
        private val plannedMedicineRepository: PlannedMedicineRepository,
        private val medicinePlanRepository: MedicinePlanRepository
) {

    fun overwritePlannedMedicines(registeredUser: RegisteredUser, postDtoList: List<PlannedMedicinePostDto>): List<PostResponseDto> {
        plannedMedicineRepository.deleteAllByMedicinePlanMedicineRegisteredUser(registeredUser)
        val postResponseDtoList = mutableListOf<PostResponseDto>()
        postDtoList.forEach { postDto ->
            val newPlannedMedicine = postDto.toPlannedMedicineEntity(medicinePlanRepository)
            val savedPlannedMedicine = plannedMedicineRepository.save(newPlannedMedicine)
            val postResponseDto = PostResponseDto(localId = postDto.plannedMedicineLocalId, remoteId = savedPlannedMedicine.plannedMedicineId)
            postResponseDtoList.add(postResponseDto)
        }
        return postResponseDtoList
    }

    fun getAllPlannedMedicines(registeredUser: RegisteredUser): List<PlannedMedicineGetDto> {
        val allPlannedMedicines = plannedMedicineRepository.findAllByMedicinePlanMedicineRegisteredUser(registeredUser)
        return allPlannedMedicines.map { plannedMedicine -> PlannedMedicineGetDto(plannedMedicine) }
    }
}