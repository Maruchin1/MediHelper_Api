package com.example.medihelperapi.service

import com.example.medihelperapi.dto.MedicineDto
import com.example.medihelperapi.model.RegisteredUser
import com.example.medihelperapi.repository.MedicineRepository
import org.springframework.stereotype.Service

@Service
class MedicineService(private val medicineRepository: MedicineRepository) {

    fun synchronizeMedicines(
            registeredUser: RegisteredUser,
            insertUpdateDtoList: List<MedicineDto>,
            deleteRemoteIdList: List<Long>
    ): List<MedicineDto> {
        val insertDtoList = insertUpdateDtoList.filter { it.medicineRemoteId == null }
        val updateDtoList = insertUpdateDtoList.filter { it.medicineRemoteId != null }

        val localIdRemoteIdPairList = mutableListOf<Pair<Int, Long>>()
        insertDtoList.forEach { medicineDto ->
            val newMedicine = medicineDto.toEntity(registeredUser)
            val savedMedicine = medicineRepository.save(newMedicine)
            localIdRemoteIdPairList.add(Pair(medicineDto.medicineLocalId!!, savedMedicine.medicineId))
        }
        updateDtoList.forEach { medicineDto ->
            if (medicineRepository.existsById(medicineDto.medicineRemoteId!!)) {
                val updatedMedicine = medicineDto.toEntity(registeredUser)
                medicineRepository.save(updatedMedicine)
            }
        }
        deleteRemoteIdList.forEach { medicineId ->
            if (medicineRepository.existsById(medicineId)) {
                medicineRepository.deleteById(medicineId)
            }
        }

        return medicineRepository.findAllByRegisteredUser(registeredUser).map { medicine ->
            val medicineLocalId = localIdRemoteIdPairList.find { it.second == medicine.medicineId }?.first
            MedicineDto(medicine, medicineLocalId)
        }
    }

    fun deleteAllData(registeredUser: RegisteredUser) = medicineRepository.deleteAllByRegisteredUser(registeredUser)

    fun isDataAvailable(registeredUser: RegisteredUser) = medicineRepository.countByRegisteredUser(registeredUser) > 0
}