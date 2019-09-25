package com.example.medihelperapi.service

import com.example.medihelperapi.dto.PostResponseDto
import com.example.medihelperapi.dto.SyncRequestDto
import com.example.medihelperapi.dto.medicine.MedicineGetDto
import com.example.medihelperapi.dto.medicine.MedicinePostDto
import com.example.medihelperapi.dto.MedicineDto
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

    fun synchronizeMedicines(registeredUser: RegisteredUser, syncRequestDto: SyncRequestDto<MedicineDto>): List<MedicineDto> {
        val insertDtoList = syncRequestDto.insertUpdateDtoList.filter { it.medicineRemoteId == null }
        val updateDtoList = syncRequestDto.insertUpdateDtoList.filter { it.medicineRemoteId != null }
        val deleteIdList = syncRequestDto.deleteRemoteIdList

        println("insertDtoList = $insertDtoList")
        println("updateDtoList = $updateDtoList")
        println("deleteRemoteIdList = $deleteIdList")

        insertDtoList.forEach { medicineDto ->
            val newMedicine = medicineDto.toNewMedicineEntity(registeredUser)
            medicineRepository.save(newMedicine)
        }
        updateDtoList.forEach { medicineDto ->
            if (medicineRepository.existsById(medicineDto.medicineRemoteId!!)) {
                val updatedMedicine = medicineDto.toExistingMedicineEntity(registeredUser)
                medicineRepository.save(updatedMedicine)
            }
        }
        deleteIdList.forEach { medicineId ->
            if (medicineRepository.existsById(medicineId)) {
                medicineRepository.deleteById(medicineId)
            }
        }

        return medicineRepository.findAllByRegisteredUser(registeredUser).map { medicine -> MedicineDto(medicine) }
    }
}