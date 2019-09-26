package com.example.medihelperapi.service

import com.example.medihelperapi.dto.PlannedMedicineDto
import com.example.medihelperapi.model.RegisteredUser
import com.example.medihelperapi.repository.MedicinePlanRepository
import com.example.medihelperapi.repository.PlannedMedicineRepository
import org.springframework.stereotype.Service

@Service
class PlannedMedicineService(
        private val plannedMedicineRepository: PlannedMedicineRepository,
        private val medicinePlanRepository: MedicinePlanRepository
) {
    fun synchronizePlannedMedicines(
            registeredUser: RegisteredUser,
            insertUpdateDtoList: List<PlannedMedicineDto>,
            deleteRemoteIdList: List<Long>
    ): List<PlannedMedicineDto> {
        val insertDtoList = insertUpdateDtoList.filter { it.plannedMedicineRemoteId == null }
        val updateDtoList = insertUpdateDtoList.filter { it.plannedMedicineRemoteId != null }

        val localIdRemoteIdPairList = mutableListOf<Pair<Int, Long>>()
        insertDtoList.forEach { plannedMedicineDto ->
            val newPlannedMedicine = plannedMedicineDto.toPlannedMedicineEntity(medicinePlanRepository)
            val savedPlannedMedicine = plannedMedicineRepository.save(newPlannedMedicine)
            localIdRemoteIdPairList.add(Pair(plannedMedicineDto.plannedMedicineLocalId!!, savedPlannedMedicine.plannedMedicineId))
        }
        updateDtoList.forEach { plannedMedicineDto ->
            if (plannedMedicineRepository.existsById(plannedMedicineDto.plannedMedicineRemoteId!!)) {
                val updatedPlannedMedicine = plannedMedicineDto.toPlannedMedicineEntity(medicinePlanRepository)
                plannedMedicineRepository.save(updatedPlannedMedicine)
            }
        }
        deleteRemoteIdList.forEach { plannedMedicineId ->
            if (plannedMedicineRepository.existsById(plannedMedicineId)) {
                plannedMedicineRepository.deleteById(plannedMedicineId)
            }
        }

        return plannedMedicineRepository.findAllByMedicinePlanMedicineRegisteredUser(registeredUser).map { plannedMedicine ->
            val plannedMedicineLocalId = localIdRemoteIdPairList.find { it.second == plannedMedicine.plannedMedicineId }?.first
            PlannedMedicineDto(plannedMedicine, plannedMedicineLocalId)
        }
    }
}