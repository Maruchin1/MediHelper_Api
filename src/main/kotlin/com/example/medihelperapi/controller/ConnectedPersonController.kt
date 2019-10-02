package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.MedicineDto
import com.example.medihelperapi.dto.MedicinePlanDto
import com.example.medihelperapi.dto.PlannedMedicineDto
import com.example.medihelperapi.service.ConnectedPersonService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/connected-persons")
class ConnectedPersonController(private val connectedPersonService: ConnectedPersonService) {

    @GetMapping("/data/medicines")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun getMedicines(): List<MedicineDto> = connectedPersonService.getMedicines()


    @GetMapping("/data/medicines-plans")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun getMedicinesPlans(): List<MedicinePlanDto> = connectedPersonService.getMedicinesPlans()

    @PutMapping("/data/planned-medicines")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun synchronizePlannedMedicines(updateDtoList: List<PlannedMedicineDto>): List<PlannedMedicineDto> = connectedPersonService.synchronizePlannedMedicines(updateDtoList)
}