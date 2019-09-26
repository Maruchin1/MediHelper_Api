package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.NewPasswordDto
import com.example.medihelperapi.dto.SyncRequestDto
import com.example.medihelperapi.dto.SyncResponseDto
import com.example.medihelperapi.dto.UserCredentialsDto
import com.example.medihelperapi.service.MedicinePlanService
import com.example.medihelperapi.service.MedicineService
import com.example.medihelperapi.service.PersonService
import com.example.medihelperapi.service.RegisteredUserService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/registered-users")
class RegisteredUserController(
        private val registeredUserService: RegisteredUserService,
        private val medicineService: MedicineService,
        private val personService: PersonService,
        private val medicinePlanService: MedicinePlanService
) {

    @PatchMapping("/password")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun changeUserPassword(@RequestBody newPassword: NewPasswordDto) {
        registeredUserService.changePassword(registeredUserService.getLoggedUser(), newPassword)
    }

//    @GetMapping("/data")
//    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
//    fun hasData(): Boolean {
//        return registeredUserService.hasData(registeredUserService.getLoggedUser())
//    }

    @PutMapping("/data")
    fun synchronizeData(@RequestBody syncRequestDto: SyncRequestDto): SyncResponseDto {
        val registeredUser = registeredUserService.getLoggedUser()
        val synchronizedMedicineDtoList = medicineService.synchronizeMedicines(
                registeredUser = registeredUser,
                insertUpdateDtoList = syncRequestDto.insertUpdateMedicineDtoList,
                deleteRemoteIdList = syncRequestDto.deleteMedicineRemoteIdList
        )
        val synchronizedPersonsDtoList = personService.synchronizePersons(
                registeredUser = registeredUser,
                insertUpdateDtoList = syncRequestDto.insertUpdatePersonDtoList,
                deleteRemoteIdList = syncRequestDto.deletePersonRemoteIdList
        )
        val synchronizedMedicinePlanDtoList = medicinePlanService.synchronizeMedicinesPlans(
                registeredUser = registeredUser,
                insertUpdateDtoList = syncRequestDto.insertUpdateMedicinePlanDtoList,
                deleteRemoteIdList = syncRequestDto.deleteMedicinePlanRemoteIdList
        )
        return SyncResponseDto(
                medicineDtoList = synchronizedMedicineDtoList,
                personDtoList = synchronizedPersonsDtoList,
                medicinePlanDtoList = synchronizedMedicinePlanDtoList
        )
    }
}