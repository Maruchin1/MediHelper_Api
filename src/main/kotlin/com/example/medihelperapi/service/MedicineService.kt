package com.example.medihelperapi.service

import com.example.medihelperapi.dto.MedicineDto
import com.example.medihelperapi.model.Medicine
import com.example.medihelperapi.model.MedicineId
import com.example.medihelperapi.model.RegisteredUser
import com.example.medihelperapi.repository.MedicineRepository
import com.example.medihelperapi.repository.RegisteredUserRepository
import org.springframework.stereotype.Service

@Service
class MedicineService(
        private val medicineRepository: MedicineRepository,
        private val registeredUserRepository: RegisteredUserRepository
) {

    fun insertNewMedicine(registeredUserEmail: String, medicineDto: MedicineDto) {
        val registeredUser = findRegisteredUserByEmail(registeredUserEmail)
        val existingMedicineOptional = medicineRepository.findById(MedicineId(registeredUser.registeredUserID, medicineDto.medicineName))
        if (existingMedicineOptional.isPresent) {
            val existingMedicine = existingMedicineOptional.get()
            if (medicineDto.operationTime.isAfter(existingMedicine.lastModificationTime)) {
                medicineRepository.delete(existingMedicine)
                medicineRepository.save(createMedicineEntity(registeredUser.registeredUserID, medicineDto))
            }
        } else {
            medicineRepository.save(createMedicineEntity(registeredUser.registeredUserID, medicineDto))
        }
    }

    fun updateMedicine(registeredUserEmail: String, medicineDto: MedicineDto) {
        val registeredUser = findRegisteredUserByEmail(registeredUserEmail)

    }

    private fun createMedicineEntity(registeredUserID: Long, medicineDto: MedicineDto) = Medicine(
            medicineID = MedicineId(registeredUserID, medicineDto.medicineName),
            medicineUnit = medicineDto.medicineUnit,
            expireDate = medicineDto.expireDate,
            packageSize = medicineDto.packageSize,
            currState = medicineDto.currState,
            additionalInfo = medicineDto.additionalInfo,
            lastModificationTime = medicineDto.operationTime
    )

    private fun findRegisteredUserByEmail(email: String): RegisteredUser = registeredUserRepository.findByEmail(email)
            .orElseThrow { UserNotFoundException() }
}