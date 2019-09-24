package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.PostResponseDto
import com.example.medihelperapi.dto.plannedmedicine.PlannedMedicineGetDto
import com.example.medihelperapi.dto.plannedmedicine.PlannedMedicinePostDto
import com.example.medihelperapi.service.PlannedMedicineService
import com.example.medihelperapi.service.RegisteredUserService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PlannedMedicineController(
        private val registeredUserService: RegisteredUserService,
        private val plannedMedicineService: PlannedMedicineService
) {
    @PostMapping("/planned-medicines/overwrite")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun overwritePlannedMedicines(@RequestBody postDtoList: List<PlannedMedicinePostDto>): List<PostResponseDto> {
        return plannedMedicineService.overwritePlannedMedicines(registeredUserService.getLoggedUser(), postDtoList)
    }

    @GetMapping("/planned-medicines")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun getAllPlannedMedicines(): List<PlannedMedicineGetDto> {
        return plannedMedicineService.getAllPlannedMedicines(registeredUserService.getLoggedUser())
    }
}