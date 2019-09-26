package com.example.medihelperapi.service

import com.example.medihelperapi.dto.MedicineDto
import com.example.medihelperapi.model.RegisteredUser
import com.example.medihelperapi.repository.MedicineRepository
import org.springframework.stereotype.Service

@Service
class MedicineService(private val medicineRepository: MedicineRepository) {

//    fun overwriteMedicines(registeredUser: RegisteredUser, postDtoList: List<MedicinePostDto>): List<PostResponseDto> {
//        medicineRepository.deleteAllByRegisteredUser(registeredUser)
//        val postResponseDtoList = mutableListOf<PostResponseDto>()
//        postDtoList.forEach { medicinePostDto ->
//            val newMedicine = medicinePostDto.toMedicineEntity(registeredUser)
//            val savedMedicine = medicineRepository.save(newMedicine)
//            val postResponseDto = PostResponseDto(localId = medicinePostDto.medicineLocalId, remoteId = savedMedicine.medicineId)
//            postResponseDtoList.add(postResponseDto)
//        }
//        return postResponseDtoList
//    }
//
//    fun getAllMedicines(registeredUser: RegisteredUser): List<MedicineGetDto> {
//        val allMedicineList = medicineRepository.findAllByRegisteredUser(registeredUser)
//        return allMedicineList.map { medicine -> MedicineGetDto(medicine) }
//    }

    fun synchronizeMedicines(
            registeredUser: RegisteredUser,
            insertUpdateDtoList: List<MedicineDto>,
            deleteRemoteIdList: List<Long>
    ): List<MedicineDto> {
        val insertDtoList = insertUpdateDtoList.filter { it.medicineRemoteId == null }
        val updateDtoList = insertUpdateDtoList.filter { it.medicineRemoteId != null }

        println("insertDtoList = $insertDtoList")
        println("updateDtoList = $updateDtoList")
        println("deleteRemoteIdList = $deleteRemoteIdList")

        insertDtoList.forEach { medicineDto ->
            val newMedicine = medicineDto.toEntity(registeredUser)
            medicineRepository.save(newMedicine)
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

        return medicineRepository.findAllByRegisteredUser(registeredUser).map { medicine -> MedicineDto(medicine) }
    }
}