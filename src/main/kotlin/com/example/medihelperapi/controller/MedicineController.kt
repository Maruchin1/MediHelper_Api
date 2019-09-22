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

    @PostMapping("/medicines")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun insertNewMedicine(@RequestBody medicinePostDto: MedicinePostDto): PostResponseDto {
        val userEmail = SecurityContextHolder.getContext().getAuthenticatedUserEmail()
        return medicineService.insertNewMedicine(userEmail, medicinePostDto)
    }

    @PostMapping("/medicines/list")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun insertNewMedicineList(@RequestBody medicinePostDtoList: List<MedicinePostDto>): List<PostResponseDto> {
        val userEmail = SecurityContextHolder.getContext().getAuthenticatedUserEmail()
        val postResponseDtoList = mutableListOf<PostResponseDto>()
        medicinePostDtoList.forEach { medicinePostDto ->
            postResponseDtoList.add(medicineService.insertNewMedicine(userEmail, medicinePostDto))
        }
        return postResponseDtoList
    }

    @PutMapping("/medicines/{id}")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun updateMedicine(@PathVariable(value = "id") medicineId: Long, @RequestBody medicinePutDto: MedicinePutDto) {
        medicineService.updateMedicineData(medicineId, medicinePutDto)
    }

    @DeleteMapping("/medicines/{id}")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun deleteMedicine(@PathVariable(value = "id") medicineId: Long) {
        medicineService.deleteMedicine(medicineId)
    }

    @GetMapping("/medicines")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun getAllMedicines(): List<MedicineGetDto> {
        val userEmail = SecurityContextHolder.getContext().getAuthenticatedUserEmail()
        return medicineService.getAllMedicines(userEmail)
    }
}