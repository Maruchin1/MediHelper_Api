package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.MedicineDto
import com.example.medihelperapi.dto.MedicinePlanDto
import com.example.medihelperapi.dto.PlannedMedicineDto
import com.example.medihelperapi.service.ConnectedPersonService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/connected-persons")
class ConnectedPersonController(private val connectedPersonService: ConnectedPersonService) {

    @GetMapping("/data/medicines")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    @ApiResponses(ApiResponse(code = 404, message = "User not found in database"))
    fun getMedicines(): List<MedicineDto> = connectedPersonService.getMedicines()

    @GetMapping("/data/medicines-plans")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    @ApiResponses(ApiResponse(code = 404, message = "User not found in database"))
    fun getMedicinesPlans(): List<MedicinePlanDto> = connectedPersonService.getMedicinesPlans()

    @PutMapping("/data/planned-medicines")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    @ApiResponses(ApiResponse(code = 404, message = "User not found in database"))
    fun synchronizePlannedMedicines(@RequestBody updateDtoList: List<PlannedMedicineDto>): List<PlannedMedicineDto> = connectedPersonService.synchronizePlannedMedicines(updateDtoList)
}