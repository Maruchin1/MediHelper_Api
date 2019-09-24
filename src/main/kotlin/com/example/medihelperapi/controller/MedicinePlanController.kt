package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.medicineplan.MedicinePlanGetDto
import com.example.medihelperapi.dto.medicineplan.MedicinePlanPostDto
import com.example.medihelperapi.dto.PostResponseDto
import com.example.medihelperapi.service.MedicinePlanService
import com.example.medihelperapi.service.RegisteredUserService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MedicinePlanController(
        private val medicinePlanService: MedicinePlanService,
        private val registeredUserService: RegisteredUserService
) {
    @PostMapping("/medicines-plans/overwrite")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun overwriteMedicinesPlans(@RequestBody postDtoList: List<MedicinePlanPostDto>): List<PostResponseDto> {
        return medicinePlanService.overWriteMedicinesPlans(registeredUserService.getLoggedUser(), postDtoList)
    }

    @GetMapping("/medicines-plans")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun getAllMedicinesPlans(): List<MedicinePlanGetDto> {
        return medicinePlanService.getAllMedicinesPlans(registeredUserService.getLoggedUser())
    }

}