package com.example.medihelperapi.service

import com.example.medihelperapi.dto.LocalIdRemoteIdPair
import com.example.medihelperapi.dto.PostResponseDto
import com.example.medihelperapi.dto.SyncRequestDto
import com.example.medihelperapi.dto.medicine.MedicineGetDto
import com.example.medihelperapi.dto.medicine.MedicinePostDto
import com.example.medihelperapi.dto.medicine.MedicineDto
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

    fun synchronizeMedicines(
            registeredUser: RegisteredUser,
            syncRequestDto: SyncRequestDto<MedicineDto>
    ): List<MedicineDto> {
        val medicinesToInsert = syncRequestDto.insertUpdateDtoList.filter {
            it.medicineLocalId != null && it.medicineRemoteId == null
        }
        val medicinesToUpdate = syncRequestDto.insertUpdateDtoList.filter {
            it.medicineLocalId == null && it.medicineRemoteId != null
        }

        val localIdRemoteIdPairList = mutableListOf<LocalIdRemoteIdPair>()
        medicinesToInsert.forEach { medicineDto ->
            val newMedicine = medicineDto.toNewMedicineEntity(registeredUser)
            val savedMedicine = medicineRepository.save(newMedicine)
            val localIdRemoteIdPair = LocalIdRemoteIdPair(
                    localId = medicineDto.medicineLocalId!!,
                    remoteId = savedMedicine.medicineId
            )
            localIdRemoteIdPairList.add(localIdRemoteIdPair)
        }

        medicinesToUpdate.forEach { medicineDto ->
            if (medicineRepository.existsById(medicineDto.medicineRemoteId!!)) {
                val updatedMedicine = medicineDto.toExistingMedicineEntity(registeredUser, medicineDto.medicineRemoteId)
                medicineRepository.save(updatedMedicine)
            }
        }

        return medicineRepository.findAllByRegisteredUser(registeredUser).map { medicine ->
            val localIdRemoteIdPair = localIdRemoteIdPairList.find { it.remoteId == medicine.medicineId }
            MedicineDto(medicine, localIdRemoteIdPair?.localId)
        }
    }
}