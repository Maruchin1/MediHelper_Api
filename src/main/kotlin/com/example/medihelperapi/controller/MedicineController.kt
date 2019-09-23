package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.MedicineGetDto
import com.example.medihelperapi.dto.MedicinePostDto
import com.example.medihelperapi.dto.MedicinePutDto
import com.example.medihelperapi.dto.PostResponseDto
import com.example.medihelperapi.getAuthenticatedUserEmail
import com.example.medihelperapi.service.MedicineService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
class MedicineController(private val medicineService: MedicineService) {

//    @PostMapping("/medicines")
//    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
//    fun insertNewMedicine(@RequestBody medicinePostDto: MedicinePostDto): PostResponseDto {
//        val userEmail = SecurityContextHolder.getContext().getAuthenticatedUserEmail()
//        return medicineService.insertNewMedicine(userEmail, medicinePostDto)
//    }

    @PostMapping("/medicines/overwrite")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun overwriteMedicines(@RequestBody medicinePostDtoList: List<MedicinePostDto>): List<PostResponseDto> {
        val userEmail = SecurityContextHolder.getContext().getAuthenticatedUserEmail()
        return medicineService.overwriteMedicines(userEmail, medicinePostDtoList)
    }

//    @PutMapping("/medicines/{id}")
//    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
//    fun updateMedicine(@PathVariable(value = "id") medicineId: Long, @RequestBody medicinePutDto: MedicinePutDto) {
//        medicineService.updateMedicineData(medicineId, medicinePutDto)
//    }
//
//    @DeleteMapping("/medicines/{id}")
//    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
//    fun deleteMedicine(@PathVariable(value = "id") medicineId: Long) {
//        medicineService.deleteMedicine(medicineId)
//    }

    @GetMapping("/medicines")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun getAllMedicines(): List<MedicineGetDto> {
        val userEmail = SecurityContextHolder.getContext().getAuthenticatedUserEmail()
        return medicineService.getAllMedicines(userEmail)
    }
}