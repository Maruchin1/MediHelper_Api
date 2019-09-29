package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.*
import com.example.medihelperapi.service.*
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/registered-users")
class RegisteredUserController(
        private val registeredUserService: RegisteredUserService,
        private val medicineService: MedicineService,
        private val personService: PersonService,
        private val medicinePlanService: MedicinePlanService,
        private val plannedMedicineService: PlannedMedicineService
) {

    @PatchMapping("/password")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun changeUserPassword(@RequestBody newPassword: NewPasswordDto) {
        registeredUserService.changePassword(registeredUserService.getLoggedUser(), newPassword)
    }

    @PutMapping("/synchronization/medicines")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun synchronizeMedicines(@RequestBody syncRequestDto: SyncRequestDto<MedicineDto>): List<MedicineDto> {
        println("synchronizeMedicines")
        println(syncRequestDto.toString())
        return medicineService.synchronizeMedicines(
                registeredUser = registeredUserService.getLoggedUser(),
                insertUpdateDtoList = syncRequestDto.insertUpdateDtoList,
                deleteRemoteIdList = syncRequestDto.deleteRemoteIdList
        )
    }

    @PutMapping("/synchronization/persons")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun synchronizePersons(@RequestBody syncRequestDto: SyncRequestDto<PersonDto>): List<PersonDto> {
        println("synchronizePersons")
        println(syncRequestDto.toString())
        return personService.synchronizePersons(
                registeredUser = registeredUserService.getLoggedUser(),
                insertUpdateDtoList = syncRequestDto.insertUpdateDtoList,
                deleteRemoteIdList = syncRequestDto.deleteRemoteIdList
        )
    }

    @PutMapping("/synchronization/medicines-plans")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun synchronizeMedicinesPlans(@RequestBody syncRequestDto: SyncRequestDto<MedicinePlanDto>): List<MedicinePlanDto> {
        println("synchronizeMedicinesPlans")
        println(syncRequestDto.toString())
        return medicinePlanService.synchronizeMedicinesPlans(
                registeredUser = registeredUserService.getLoggedUser(),
                insertUpdateDtoList = syncRequestDto.insertUpdateDtoList,
                deleteRemoteIdList = syncRequestDto.deleteRemoteIdList
        )
    }

    @PutMapping("/synchronization/planned-medicines")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun synchronizePlannedMedicines(@RequestBody syncRequestDto: SyncRequestDto<PlannedMedicineDto>): List<PlannedMedicineDto> {
        println("synchronizeMedicinesPlans")
        println(syncRequestDto.toString())
        return plannedMedicineService.synchronizePlannedMedicines(
                registeredUser = registeredUserService.getLoggedUser(),
                insertUpdateDtoList = syncRequestDto.insertUpdateDtoList,
                deleteRemoteIdList = syncRequestDto.deleteRemoteIdList
        )
    }

    @DeleteMapping("/data")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun deleteAllData() {
        val loggedUser = registeredUserService.getLoggedUser()
        medicineService.deleteAllData(loggedUser)
        personService.deleteAllData(loggedUser)
    }

    @GetMapping("/data/available")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun isDataAvailable(): Boolean {
        val loggedUser = registeredUserService.getLoggedUser()
        return medicineService.isDataAvailable(loggedUser) ||
                personService.isDataAvailable(loggedUser)
    }
}