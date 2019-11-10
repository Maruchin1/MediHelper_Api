package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.*
import com.example.medihelperapi.service.*
import io.swagger.annotations.*
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/registered-users")
class RegisteredUserController(private val registeredUserService: RegisteredUserService) {

    @PatchMapping("/password")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    @ApiResponses(ApiResponse(code = 404, message = "User not found in database"))
    fun changeUserPassword(@RequestBody newPassword: NewPasswordDto) {
        registeredUserService.changePassword(newPassword)
    }

    @PutMapping("/data/medicines")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    @ApiResponses(ApiResponse(code = 404, message = "User not found in database"))
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
    @ApiResponses(ApiResponse(code = 404, message = "User not found in database"))
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
    @ApiResponses(
            ApiResponse(code = 404, message = "User not found in database"),
            ApiResponse(code = 404, message = "Medicine not found in database"),
            ApiResponse(code = 404, message = "Person not found in database")
    )
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
    @ApiResponses(
            ApiResponse(code = 404, message = "User not found in database"),
            ApiResponse(code = 404, message = "MedicinePlan not found in database")
    )
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
    @ApiResponses(ApiResponse(code = 404, message = "User not found in database"))
    fun deleteAllData() {
        registeredUserService.deleteAllData()
    }
}