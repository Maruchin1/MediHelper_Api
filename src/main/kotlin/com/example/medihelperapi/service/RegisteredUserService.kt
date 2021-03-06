package com.example.medihelperapi.service

import com.example.medihelperapi.dto.*
import com.example.medihelperapi.model.DaysOfWeek
import com.example.medihelperapi.model.PlannedMedicine
import com.example.medihelperapi.model.RegisteredUser
import com.example.medihelperapi.model.TimeDose
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
        private val plannedMedicineRepository: PlannedMedicineRepository,
        private val timeDoseRepository: TimeDoseRepository,
        private val daysOfWeekRepository: DaysOfWeekRepository,
        private val mapper: EntityDtoMapper
) {
    private val currUser: RegisteredUser
        get() {
            val user = SecurityContextHolder.getContext().authentication.principal as User
            return registeredUserRepository.findById(user.username.toLong()).orElseThrow { UserNotFoundException() }
        }

    fun changePassword(newPassword: NewPasswordDto) = registeredUserRepository.save(
            currUser.copy(password = passwordEncoder.encode(newPassword.value))
    )

    @Transactional
    fun deleteAllData() {
        val persons = personRepository.findAllByRegisteredUser(currUser)
        val medicines = medicineRepository.findAllByRegisteredUser(currUser)
        val medicinesPlans = medicinePlanRepository.findAllByMedicineRegisteredUser(currUser)
        val timesDoses = mutableListOf<TimeDose>()
        val daysOfWeek = mutableListOf<DaysOfWeek>()
        medicinesPlans.forEach {
            timesDoses.addAll(it.timeDoseList)
            if (it.daysOfWeek != null) {
                daysOfWeek.add(it.daysOfWeek!!)
            }
        }
        val plannedMedicines = plannedMedicineRepository.findAllByMedicinePlanMedicineRegisteredUser(currUser)

        timeDoseRepository.deleteAll(timesDoses)
        daysOfWeekRepository.deleteAll(daysOfWeek)
        plannedMedicineRepository.deleteAll(plannedMedicines)
        medicinePlanRepository.deleteAll(medicinesPlans)
        medicineRepository.deleteAll(medicines)
        personRepository.deleteAll(persons)
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
            val newMedicine = mapper.medicineDtoToEntity(medicineDto, currUser)
            val savedMedicine = medicineRepository.save(newMedicine)
            localIdRemoteIdPairList.add(Pair(medicineDto.medicineLocalId!!, savedMedicine.medicineId))
        }
        updateDtoList.forEach { medicineDto ->
            if (medicineRepository.existsById(medicineDto.medicineRemoteId!!)) {
                val updatedMedicine = mapper.medicineDtoToEntity(medicineDto, currUser)
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
            mapper.medicineEntityToDto(medicine, medicineLocalId)
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
            val newPerson = mapper.personDtoToEntity(personDto, currUser).apply {
                connectionKey = generateRandomUniqueKey()
            }
            val savedPerson = personRepository.save(newPerson)
            localIdRemoteIdPairList.add(Pair(personDto.personLocalId!!, savedPerson.personId))
        }
        updateDtoList.forEach { personDto ->
            if (personRepository.existsById(personDto.personRemoteId!!)) {
                val updatedPerson = mapper.personDtoToEntity(personDto, currUser)
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
            mapper.personEntityToDto(person, personLocalId)
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
            val newMedicinePlan = mapper.medicinePlanDtoToEntity(medicinePlanDto)
            newMedicinePlan.daysOfWeek = medicinePlanDto.daysOfWeekDto?.let {
                mapper.daysOfWeekDtoToEntity(it, newMedicinePlan)
            }
            newMedicinePlan.timeDoseList = medicinePlanDto.timeDoseDtoList.map {
                mapper.timeDoseDtoToEntity(it, newMedicinePlan)
            }
            val savedMedicinePlan = medicinePlanRepository.save(newMedicinePlan)
            localIdRemoteIdPairList.add(Pair(medicinePlanDto.medicinePlanLocalId!!, savedMedicinePlan.medicinePlanId))
        }
        updateDtoList.forEach { medicinePlanDto ->
            if (medicinePlanRepository.existsById(medicinePlanDto.medicinePlanRemoteId!!)) {
                val updatedMedicinePlan = mapper.medicinePlanDtoToEntity(medicinePlanDto)
                var daysOfWeek = updatedMedicinePlan.daysOfWeek
                var timeDoseList = updatedMedicinePlan.timeDoseList

                if (daysOfWeek != null) {
                    daysOfWeekRepository.delete(daysOfWeek)
                }
                if (timeDoseList.isNotEmpty()) {
                    timeDoseRepository.deleteAll(timeDoseList)
                }

                daysOfWeek = medicinePlanDto.daysOfWeekDto?.let {
                    mapper.daysOfWeekDtoToEntity(it, updatedMedicinePlan)
                }
                timeDoseList = medicinePlanDto.timeDoseDtoList.map {
                    mapper.timeDoseDtoToEntity(it, updatedMedicinePlan)
                }

                updatedMedicinePlan.daysOfWeek = daysOfWeek
                updatedMedicinePlan.timeDoseList = timeDoseList

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
            mapper.medicinePlanEntityToDto(medicinePlan, medicinePlanLocalId)
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
            val newPlannedMedicine = mapper.plannedMedicineDtoToEntity(plannedMedicineDto)
            val savedPlannedMedicine = plannedMedicineRepository.save(newPlannedMedicine)
            localIdRemoteIdPairList.add(Pair(plannedMedicineDto.plannedMedicineLocalId!!, savedPlannedMedicine.plannedMedicineId))
        }
        updateDtoList.forEach { plannedMedicineDto ->
            if (plannedMedicineRepository.existsById(plannedMedicineDto.plannedMedicineRemoteId!!)) {
                val updatedPlannedMedicine = mapper.plannedMedicineDtoToEntity(plannedMedicineDto)
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
            mapper.plannedMedicineEntityToDto(plannedMedicine, plannedMedicineLocalId)
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