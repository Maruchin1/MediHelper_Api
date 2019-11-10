package com.example.medihelperapi.service

import com.example.medihelperapi.dto.*
import com.example.medihelperapi.model.*
import com.example.medihelperapi.repository.MedicinePlanRepository
import com.example.medihelperapi.repository.MedicineRepository
import com.example.medihelperapi.repository.PersonRepository
import org.springframework.stereotype.Service

@Service
class EntityDtoMapper(
        private val medicineRepository: MedicineRepository,
        private val personRepository: PersonRepository,
        private val medicinePlanRepository: MedicinePlanRepository
) {

    fun personToConnectedPersonDto(person: Person) = ConnectedPersonDto(
            personRemoteId = person.personId,
            personName = person.personName,
            personColorResId = person.personColorResId,
            authToken = person.authToken
    )

    fun medicineEntityToDto(entity: Medicine, localId: Int?) = MedicineDto(
            medicineLocalId = localId,
            medicineRemoteId = entity.medicineId,
            medicineName = entity.medicineName,
            medicineUnit = entity.medicineUnit,
            expireDate = entity.expireDate,
            packageSize = entity.packageSize,
            currState = entity.currState,
            additionalInfo = entity.additionalInfo,
            imageName = entity.imageName
    )

    fun medicineDtoToEntity(dto: MedicineDto, user: RegisteredUser) = Medicine(
            medicineId = dto.medicineRemoteId ?: 0,
            registeredUser = user,
            medicineName = dto.medicineName,
            medicineUnit = dto.medicineUnit,
            expireDate = dto.expireDate,
            packageSize = dto.packageSize,
            currState = dto.currState,
            additionalInfo = dto.additionalInfo,
            imageName = dto.imageName
    )

    fun personEntityToDto(entity: Person, localId: Int?) = PersonDto(
            personLocalId = localId,
            personRemoteId = entity.personId,
            personName = entity.personName,
            personColorResId = entity.personColorResId,
            connectionKey = entity.connectionKey
    )

    fun personDtoToEntity(dto: PersonDto, user: RegisteredUser) = Person(
            personId = dto.personRemoteId ?: 0,
            registeredUser = user,
            personName = dto.personName,
            personColorResId = dto.personColorResId
    )

    fun medicinePlanEntityToDto(entity: MedicinePlan, localId: Int?) = MedicinePlanDto(
            medicinePlanLocalId = localId,
            medicinePlanRemoteId = entity.medicinePlanId,
            medicineRemoteId = entity.medicine.medicineId,
            personRemoteId = entity.person?.personId,
            startDate = entity.startDate,
            endDate = entity.endDate,
            durationType = entity.durationType,
            daysOfWeekDto = entity.daysOfWeek?.let { daysOfWeekEntityToDto(it) },
            intervalOfDays = entity.intervalOfDays,
            daysType = entity.daysType,
            timeDoseDtoList = entity.timeDoseList.map { timeDoseEntityToDto(it) }
    )

    fun medicinePlanDtoToEntity(dto: MedicinePlanDto) = MedicinePlan(
            medicinePlanId = dto.medicinePlanRemoteId ?: 0,
            medicine = medicineRepository.findById(dto.medicineRemoteId).orElseThrow { MedicineNotFoundException() },
            person = dto.personRemoteId?.let { personRepository.findById(it).orElseThrow { PersonNotFoundException() } },
            startDate = dto.startDate,
            endDate = dto.endDate,
            durationType = dto.durationType,
            daysOfWeek = dto.daysOfWeekDto?.let { daysOfWeekDtoToEntity(it) },
            intervalOfDays = dto.intervalOfDays,
            daysType = dto.daysType,
            timeDoseList = dto.timeDoseDtoList.map { timeDoseDtoToEntity(it) }
    )

    fun plannedMedicineEntityToDto(entity: PlannedMedicine, localId: Int?) = PlannedMedicineDto(
            plannedMedicineLocalId = localId,
            plannedMedicineRemoteId = entity.plannedMedicineId,
            medicinePlanRemoteId = entity.medicinePlan.medicinePlanId,
            plannedDate = entity.plannedDate,
            plannedTime = entity.plannedTime,
            plannedDoseSize = entity.plannedDoseSize,
            statusOfTaking = entity.statusOfTaking
    )

    fun plannedMedicineDtoToEntity(dto: PlannedMedicineDto) = PlannedMedicine(
            plannedMedicineId = dto.plannedMedicineRemoteId ?: 0,
            medicinePlan = medicinePlanRepository.findById(dto.medicinePlanRemoteId).orElseThrow { MedicinePlanNotFoundException() },
            plannedDate = dto.plannedDate,
            plannedTime = dto.plannedTime,
            plannedDoseSize = dto.plannedDoseSize,
            statusOfTaking = dto.statusOfTaking
    )

    private fun daysOfWeekEntityToDto(entity: DaysOfWeek) = DaysOfWeekDto(
            monday = entity.monday,
            tuesday = entity.tuesday,
            wednesday = entity.wednesday,
            thursday = entity.thursday,
            friday = entity.friday,
            saturday = entity.saturday,
            sunday = entity.sunday
    )

    private fun daysOfWeekDtoToEntity(dto: DaysOfWeekDto) = DaysOfWeek(
            monday = dto.monday,
            tuesday = dto.tuesday,
            wednesday = dto.wednesday,
            thursday = dto.thursday,
            friday = dto.friday,
            saturday = dto.saturday,
            sunday = dto.sunday
    )

    private fun timeDoseEntityToDto(entity: TimeDose) = TimeDoseDto(
            doseSize = entity.doseSize,
            time = entity.time
    )

    private fun timeDoseDtoToEntity(dto: TimeDoseDto) = TimeDose(
            doseSize = dto.doseSize,
            time = dto.time
    )
}