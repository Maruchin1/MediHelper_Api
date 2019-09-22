package com.example.medihelperapi.service

import com.example.medihelperapi.dto.PostResponseDto
import com.example.medihelperapi.dto.MedicineGetDto
import com.example.medihelperapi.dto.MedicinePostDto
import com.example.medihelperapi.dto.MedicinePutDto
import com.example.medihelperapi.model.RegisteredUser
import com.example.medihelperapi.repository.MedicineRepository
import com.example.medihelperapi.repository.RegisteredUserRepository
import org.springframework.stereotype.Service

@Service
class MedicineService(
        private val medicineRepository: MedicineRepository,
        private val registeredUserRepository: RegisteredUserRepository
) {

    fun overwriteMedicines(email: String, medicinePostDtoList: List<MedicinePostDto>): List<PostResponseDto> {
        medicineRepository.deleteAll()
        val registeredUser = findRegisteredUserByEmail(email)
        val postResponseDtoList = mutableListOf<PostResponseDto>()
        medicinePostDtoList.forEach { medicinePostDto ->
            val newMedicine = medicinePostDto.toMedicineEntity(registeredUser)
            val savedMedicine = medicineRepository.save(newMedicine)
            val postResponseDto = PostResponseDto(localId = medicinePostDto.medicineLocalId, remoteId = savedMedicine.medicineId)
            postResponseDtoList.add(postResponseDto)
        }
        return postResponseDtoList
    }

    fun insertNewMedicine(email: String, medicinePostDto: MedicinePostDto): PostResponseDto {
        val registeredUser = findRegisteredUserByEmail(email)
        val newMedicine = medicinePostDto.toMedicineEntity(registeredUser)
        val savedMedicine = medicineRepository.save(newMedicine)
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

    fun getAllMedicines(email: String): List<MedicineGetDto> {
        val registeredUser = findRegisteredUserByEmail(email)
        val allMedicineList = medicineRepository.findAllByRegisteredUser(registeredUser)
        return allMedicineList.map { medicine -> MedicineGetDto(medicine) }
    }

    private fun findRegisteredUserByEmail(email: String): RegisteredUser = registeredUserRepository.findByEmail(email)
            .orElseThrow { UserNotFoundException() }
}