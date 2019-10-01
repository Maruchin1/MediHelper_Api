package com.example.medihelperapi.service

import com.example.medihelperapi.dto.*
import com.example.medihelperapi.model.RegisteredUser
import com.example.medihelperapi.repository.*
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisteredUserService(
        private val registeredUserRepository: RegisteredUserRepository,
        private val passwordEncoder: PasswordEncoder,
        private val medicineRepository: MedicineRepository,
        private val personRepository: PersonRepository,
        private val medicinePlanRepository: MedicinePlanRepository,
        private val plannedMedicineRepository: PlannedMedicineRepository
) {
    private val currUser: RegisteredUser
        get() {
            val user = SecurityContextHolder.getContext().authentication.principal as User
            return registeredUserRepository.findByEmail(user.username).orElseThrow { UserNotFoundException() }
        }

    fun changePassword(newPassword: NewPasswordDto) = registeredUserRepository.save(
            currUser.copy(password = passwordEncoder.encode(newPassword.value))
    )

    @Transactional
    fun deleteAllData() {
        medicineRepository.deleteAllByRegisteredUser(currUser)
        personRepository.deleteAllByRegisteredUser(currUser)
    }

    fun isDataAvailable(): Boolean {
        return medicineRepository.countByRegisteredUser(currUser) > 0 ||
                personRepository.countByRegisteredUser(currUser) > 0
    }

    @Transactional
    fun synchronizeMedicines(
            insertUpdateDtoList: List<MedicineDto>,
            deleteRemoteIdList: List<Long>
    ): List<MedicineDto> {
        val insertDtoList = insertUpdateDtoList.filter { it.medicineRemoteId == null }
        val updateDtoList = insertUpdateDtoList.filter { it.medicineRemoteId != null }

        val localIdRemoteIdPairList = mutableListOf<Pair<Int, Long>>()
        insertDtoList.forEach { medicineDto ->
            val newMedicine = medicineDto.toEntity(currUser)
            val savedMedicine = medicineRepository.save(newMedicine)
            localIdRemoteIdPairList.add(Pair(medicineDto.medicineLocalId!!, savedMedicine.medicineId))
        }
        updateDtoList.forEach { medicineDto ->
            if (medicineRepository.existsById(medicineDto.medicineRemoteId!!)) {
                val updatedMedicine = medicineDto.toEntity(currUser)
                medicineRepository.save(updatedMedicine)
            }
        }
        deleteRemoteIdList.forEach { medicineId ->
            if (medicineRepository.existsById(medicineId)) {
                medicineRepository.deleteById(medicineId)
            }
        }

        return medicineRepository.findAllByRegisteredUser(currUser).map { medicine ->
            val medicineLocalId = localIdRemoteIdPairList.find { it.second == medicine.medicineId }?.first
            MedicineDto(medicine, medicineLocalId)
        }
    }

    @Transactional
    fun synchronizePersons(
            insertUpdateDtoList: List<PersonDto>,
            deleteRemoteIdList: List<Long>
    ): List<PersonDto> {
        val insertDtoList = insertUpdateDtoList.filter { it.personRemoteId == null }
        val updateDtoList = insertUpdateDtoList.filter { it.personRemoteId != null }

        val localIdRemoteIdPairList = mutableListOf<Pair<Int, Long>>()
        insertDtoList.forEach { personDto ->
            val newPerson = personDto.toEntity(currUser).apply {
                connectionKey = generateRandomUniqueKey()
            }
            val savedPerson = personRepository.save(newPerson)
            localIdRemoteIdPairList.add(Pair(personDto.personLocalId!!, savedPerson.personId))
        }
        updateDtoList.forEach { personDto ->
            if (personRepository.existsById(personDto.personRemoteId!!)) {
                val updatedPerson = personDto.toEntity(currUser)
                personRepository.save(updatedPerson)
            }
        }
        deleteRemoteIdList.forEach { personId ->
            if (personRepository.existsById(personId)) {
                personRepository.deleteById(personId)
            }
        }

        return personRepository.findAllByRegisteredUser(currUser).map { person ->
            val personLocalId = localIdRemoteIdPairList.find { it.second == person.personId }?.first
            PersonDto(person, personLocalId)
        }
    }

    @Transactional
    fun synchronizeMedicinesPlans(
            insertUpdateDtoList: List<MedicinePlanDto>,
            deleteRemoteIdList: List<Long>
    ): List<MedicinePlanDto> {
        val insertDtoList = insertUpdateDtoList.filter { it.medicinePlanRemoteId == null }
        val updateDtoList = insertUpdateDtoList.filter { it.medicinePlanRemoteId != null }

        val localIdRemoteIdPairList = mutableListOf<Pair<Int, Long>>()
        insertDtoList.forEach { medicinePlanDto ->
            val newMedicinePlan = medicinePlanDto.toEntity(medicineRepository, personRepository)
            val savedMedicinePlan = medicinePlanRepository.save(newMedicinePlan)
            localIdRemoteIdPairList.add(Pair(medicinePlanDto.medicinePlanLocalId!!, savedMedicinePlan.medicinePlanId))
        }
        updateDtoList.forEach { medicinePlanDto ->
            if (medicinePlanRepository.existsById(medicinePlanDto.medicinePlanRemoteId!!)) {
                val updatedMedicinePlan = medicinePlanDto.toEntity(medicineRepository, personRepository)
                medicinePlanRepository.save(updatedMedicinePlan)
            }
        }
        deleteRemoteIdList.forEach { medicinePlanId ->
            if (medicinePlanRepository.existsById(medicinePlanId)) {
                medicinePlanRepository.deleteById(medicinePlanId)
            }
        }

        return medicinePlanRepository.findAllByMedicineRegisteredUser(currUser).map { medicinePlan ->
            val medicinePlanLocalId = localIdRemoteIdPairList.find { it.second == medicinePlan.medicinePlanId }?.first
            MedicinePlanDto(medicinePlan, medicinePlanLocalId)
        }
    }

    @Transactional
    fun synchronizePlannedMedicines(
            insertUpdateDtoList: List<PlannedMedicineDto>,
            deleteRemoteIdList: List<Long>
    ): List<PlannedMedicineDto> {
        val insertDtoList = insertUpdateDtoList.filter { it.plannedMedicineRemoteId == null }
        val updateDtoList = insertUpdateDtoList.filter { it.plannedMedicineRemoteId != null }

        val localIdRemoteIdPairList = mutableListOf<Pair<Int, Long>>()
        insertDtoList.forEach { plannedMedicineDto ->
            val newPlannedMedicine = plannedMedicineDto.toEntity(medicinePlanRepository)
            val savedPlannedMedicine = plannedMedicineRepository.save(newPlannedMedicine)
            localIdRemoteIdPairList.add(Pair(plannedMedicineDto.plannedMedicineLocalId!!, savedPlannedMedicine.plannedMedicineId))
        }
        updateDtoList.forEach { plannedMedicineDto ->
            if (plannedMedicineRepository.existsById(plannedMedicineDto.plannedMedicineRemoteId!!)) {
                val updatedPlannedMedicine = plannedMedicineDto.toEntity(medicinePlanRepository)
                plannedMedicineRepository.save(updatedPlannedMedicine)
            }
        }
        deleteRemoteIdList.forEach { plannedMedicineId ->
            if (plannedMedicineRepository.existsById(plannedMedicineId)) {
                plannedMedicineRepository.deleteById(plannedMedicineId)
            }
        }

        return plannedMedicineRepository.findAllByMedicinePlanMedicineRegisteredUser(currUser).map { plannedMedicine ->
            val plannedMedicineLocalId = localIdRemoteIdPairList.find { it.second == plannedMedicine.plannedMedicineId }?.first
            PlannedMedicineDto(plannedMedicine, plannedMedicineLocalId)
        }
    }

    private fun generateRandomUniqueKey(): String {
        var randomUniqueKey = ""
        do {
            randomUniqueKey = StringBuilder().apply {
                for (i in (0..5)) {
                    val randomNumber = (0..9).shuffled().first()
                    this.append(randomNumber)
                }
            }.toString()
        } while (personRepository.existsByConnectionKey(randomUniqueKey))
        return randomUniqueKey
    }
}