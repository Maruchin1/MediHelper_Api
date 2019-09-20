package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.MedicineDto
import com.example.medihelperapi.getAuthenticatedUserEmail
import com.example.medihelperapi.service.MedicineService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/medicines")
class MedicineController(private val medicineService: MedicineService) {

    @PostMapping("/insert")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun insertNewMedicine(@RequestBody medicineDto: MedicineDto) {
        val userEmail = SecurityContextHolder.getContext().getAuthenticatedUserEmail()
        medicineService.insertNewMedicine(userEmail, medicineDto)
    }
}