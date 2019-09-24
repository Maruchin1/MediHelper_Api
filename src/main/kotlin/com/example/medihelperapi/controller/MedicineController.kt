package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.medicine.MedicineGetDto
import com.example.medihelperapi.dto.medicine.MedicinePostDto
import com.example.medihelperapi.dto.PostResponseDto
import com.example.medihelperapi.service.MedicineService
import com.example.medihelperapi.service.RegisteredUserService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.web.bind.annotation.*

@RestController
class MedicineController(
        private val medicineService: MedicineService,
        private val registeredUserService: RegisteredUserService
) {
    @PostMapping("/medicines/overwrite")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun overwriteMedicines(@RequestBody postDtoList: List<MedicinePostDto>): List<PostResponseDto> {
        return medicineService.overwriteMedicines(registeredUserService.getLoggedUser(), postDtoList)
    }

    @GetMapping("/medicines")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun getAllMedicines(): List<MedicineGetDto> {
        return medicineService.getAllMedicines(registeredUserService.getLoggedUser())
    }
}