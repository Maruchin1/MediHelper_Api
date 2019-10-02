package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.*
import com.example.medihelperapi.service.*
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/registered-users")
class RegisteredUserController(private val registeredUserService: RegisteredUserService) {

    @PatchMapping("/password")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun changeUserPassword(@RequestBody newPassword: NewPasswordDto) {
        registeredUserService.changePassword(newPassword)
    }

    @PutMapping("/data/medicines")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun synchronizeMedicines(@RequestBody syncRequestDto: SyncRequestDto<MedicineDto>): List<MedicineDto> {
        println("synchronizeMedicines")
        println(syncRequestDto.toString())
        return registeredUserService.synchronizeMedicines(
                insertUpdateDtoList = syncRequestDto.insertUpdateDtoList,
                deleteRemoteIdList = syncRequestDto.deleteRemoteIdList
        )
    }

    @PutMapping("/data/persons")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun synchronizePersons(@RequestBody syncRequestDto: SyncRequestDto<PersonDto>): List<PersonDto> {
        println("synchronizePersons")
        println(syncRequestDto.toString())
        return registeredUserService.synchronizePersons(
                insertUpdateDtoList = syncRequestDto.insertUpdateDtoList,
                deleteRemoteIdList = syncRequestDto.deleteRemoteIdList
        )
    }

    @PutMapping("/data/medicines-plans")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun synchronizeMedicinesPlans(@RequestBody syncRequestDto: SyncRequestDto<MedicinePlanDto>): List<MedicinePlanDto> {
        println("synchronizeMedicinesPlans")
        println(syncRequestDto.toString())
        return registeredUserService.synchronizeMedicinesPlans(
                insertUpdateDtoList = syncRequestDto.insertUpdateDtoList,
                deleteRemoteIdList = syncRequestDto.deleteRemoteIdList
        )
    }

    @PutMapping("/data/planned-medicines")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun synchronizePlannedMedicines(@RequestBody syncRequestDto: SyncRequestDto<PlannedMedicineDto>): List<PlannedMedicineDto> {
        println("synchronizePlannedMedicines")
        println(syncRequestDto.toString())
        return registeredUserService.synchronizePlannedMedicines(
                insertUpdateDtoList = syncRequestDto.insertUpdateDtoList,
                deleteRemoteIdList = syncRequestDto.deleteRemoteIdList
        )
    }

    @DeleteMapping("/data")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun deleteAllData() {
        registeredUserService.deleteAllData()
    }

    @GetMapping("/data/available")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun isDataAvailable(): Boolean {
        return registeredUserService.isDataAvailable()
    }
}