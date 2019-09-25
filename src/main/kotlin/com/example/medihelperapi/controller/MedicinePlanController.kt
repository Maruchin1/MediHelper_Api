package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.MedicinePlanDto
import com.example.medihelperapi.dto.medicineplan.MedicinePlanGetDto
import com.example.medihelperapi.dto.medicineplan.MedicinePlanPostDto
import com.example.medihelperapi.dto.PostResponseDto
import com.example.medihelperapi.dto.SyncRequestDto
import com.example.medihelperapi.service.MedicinePlanService
import com.example.medihelperapi.service.RegisteredUserService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.web.bind.annotation.*

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

    @PutMapping("/medicines-plans/synchronize")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun synchronizeMedicinesPlans(@RequestBody syncRequestDto: SyncRequestDto<MedicinePlanDto>): List<MedicinePlanDto> {
        return medicinePlanService.synchronizeMedicinesPlans(registeredUserService.getLoggedUser(), syncRequestDto)
    }
}