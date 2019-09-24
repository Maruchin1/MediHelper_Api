package com.example.medihelperapi.service

import com.example.medihelperapi.dto.PostResponseDto
import com.example.medihelperapi.dto.medicine.MedicineGetDto
import com.example.medihelperapi.dto.medicine.MedicinePostDto
import com.example.medihelperapi.model.RegisteredUser
import com.example.medihelperapi.repository.MedicineRepository
import org.springframework.stereotype.Service

@Service
class MedicineService(private val medicineRepository: MedicineRepository) {

    fun overwriteMedicines(registeredUser: RegisteredUser, postDtoList: List<MedicinePostDto>): List<PostResponseDto> {
        medicineRepository.deleteAllByRegisteredUser(registeredUser)
        val postResponseDtoList = mutableListOf<PostResponseDto>()
        postDtoList.forEach { medicinePostDto ->
            val newMedicine = medicinePostDto.toMedicineEntity(registeredUser)
            val savedMedicine = medicineRepository.save(newMedicine)
            val postResponseDto = PostResponseDto(localId = medicinePostDto.medicineLocalId, remoteId = savedMedicine.medicineId)
            postResponseDtoList.add(postResponseDto)
        }
        return postResponseDtoList
    }

    fun getAllMedicines(registeredUser: RegisteredUser): List<MedicineGetDto> {
        val allMedicineList = medicineRepository.findAllByRegisteredUser(registeredUser)
        return allMedicineList.map { medicine -> MedicineGetDto(medicine) }
    }
}