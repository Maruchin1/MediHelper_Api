package com.example.medihelperapi.service

import com.example.medihelperapi.dto.PostResponseDto
import com.example.medihelperapi.dto.MedicineGetDto
import com.example.medihelperapi.dto.MedicinePostDto
import com.example.medihelperapi.dto.MedicinePutDto
import com.example.medihelperapi.model.Medicine
import com.example.medihelperapi.model.RegisteredUser
import com.example.medihelperapi.repository.MedicineRepository
import com.example.medihelperapi.repository.RegisteredUserRepository
import org.springframework.stereotype.Service

@Service
class MedicineService(
        private val medicineRepository: MedicineRepository,
        private val registeredUserRepository: RegisteredUserRepository
) {

    fun insertNewMedicine(registeredUserEmail: String, medicinePostDto: MedicinePostDto): PostResponseDto {
        val registeredUser = findRegisteredUserByEmail(registeredUserEmail)
        val newMedicineEntity = Medicine(
                registeredUser = registeredUser,
                medicineName = medicinePostDto.medicineName,
                medicineUnit = medicinePostDto.medicineUnit,
                expireDate = medicinePostDto.expireDate,
                packageSize = medicinePostDto.packageSize,
                currState = medicinePostDto.currState,
                additionalInfo = medicinePostDto.additionalInfo,
                lastModificationTime = medicinePostDto.operationTime
        )
        val savedMedicine = medicineRepository.save(newMedicineEntity)
        return PostResponseDto(localId = medicinePostDto.medicineLocalId, remoteId = savedMedicine.medicineId)
    }

    fun updateMedicineData(medicineId: Long, medicinePutDto: MedicinePutDto) {
        medicineRepository.findById(medicineId).map { existingMedicine ->
            if (medicinePutDto.operationTime.isAfter(existingMedicine.lastModificationTime)) {
                val updatedMedicine = existingMedicine.copy(
                        medicineName = medicinePutDto.medicineName,
                        medicineUnit = medicinePutDto.medicineUnit,
                        expireDate = medicinePutDto.expireDate,
                        packageSize = medicinePutDto.packageSize,
                        currState = medicinePutDto.currState,
                        additionalInfo = medicinePutDto.additionalInfo,
                        lastModificationTime = medicinePutDto.operationTime
                )
                medicineRepository.save(updatedMedicine)
            }
        }.orElseThrow {
            MedicineNotFoundException()
        }
    }

    fun deleteMedicine(medicineId: Long) {
        medicineRepository.findById(medicineId).map { existingMedicine ->
            medicineRepository.delete(existingMedicine)
        }.orElseThrow {
            MedicineNotFoundException()
        }
    }

    fun getAllMedicines(registeredUserEmail: String): List<MedicineGetDto> {
        val registeredUser = findRegisteredUserByEmail(registeredUserEmail)
        val allMedicineList = medicineRepository.findAllByRegisteredUser(registeredUser)
        return allMedicineList.map { medicine ->
            MedicineGetDto(
                  medicineId = medicine.medicineId,
                    medicineName = medicine.medicineName,
                    medicineUnit = medicine.medicineUnit,
                    expireDate = medicine.expireDate,
                    packageSize = medicine.packageSize,
                    currState = medicine.currState,
                    additionalInfo = medicine.additionalInfo
            )
        }
    }

    private fun findRegisteredUserByEmail(email: String): RegisteredUser = registeredUserRepository.findByEmail(email)
            .orElseThrow { UserNotFoundException() }
}