package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.MedicineGetDto
import com.example.medihelperapi.dto.MedicinePostDto
import com.example.medihelperapi.dto.PostResponseDto
import com.example.medihelperapi.getAuthenticatedUserEmail
import com.example.medihelperapi.service.MedicineService
import com.example.medihelperapi.service.RegisteredUserService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
class MedicineController(
        private val medicineService: MedicineService,
        private val registeredUserService: RegisteredUserService
) {

    @PostMapping("/medicines/overwrite")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun overwriteMedicines(@RequestBody medicinePostDtoList: List<MedicinePostDto>): List<PostResponseDto> {
        val registeredUser = registeredUserService.findByEmail(SecurityContextHolder.getContext().getAuthenticatedUserEmail())
        return medicineService.overwriteMedicines(registeredUser, medicinePostDtoList)
    }

    @GetMapping("/medicines")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun getAllMedicines(): List<MedicineGetDto> {
        val registeredUser = registeredUserService.findByEmail(SecurityContextHolder.getContext().getAuthenticatedUserEmail())
        return medicineService.getAllMedicines(registeredUser)
    }
}