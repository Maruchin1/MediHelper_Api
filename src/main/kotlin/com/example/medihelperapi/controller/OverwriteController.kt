package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.OverwriteDto
import com.example.medihelperapi.getAuthenticatedUserEmail
import com.example.medihelperapi.service.MedicinePlanService
import com.example.medihelperapi.service.MedicineService
import com.example.medihelperapi.service.PersonService
import com.example.medihelperapi.service.RegisteredUserService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OverwriteController(
        private val registeredUserService: RegisteredUserService,
        private val personService: PersonService,
        private val medicineService: MedicineService,
        private val medicinePlanService: MedicinePlanService
) {

    @PostMapping("/overwrite")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun overwriteData(@RequestBody overwriteDto: OverwriteDto) {
        val registeredUser = registeredUserService.findByEmail(SecurityContextHolder.getContext().getAuthenticatedUserEmail())
        personService.overwritePersons(registeredUser, overwriteDto.personPostDtoList)
        medicineService.overwriteMedicines(registeredUser, overwriteDto.medicinePostDtoList)
        medicinePlanService.overWriteMedicinesPlans(registeredUser, overwriteDto.medicinePlanPostDtoList)
    }
}