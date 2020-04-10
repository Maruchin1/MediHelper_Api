package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.GetMedicineDto
import com.example.medihelperapi.dto.PostMedicineDto
import com.example.medihelperapi.service.MedicinesService
import org.springframework.web.bind.annotation.*

@RestController
class MedicinesController(
    private val medicinesService: MedicinesService
) {

    @PostMapping("/medicines")
    fun addNewMedicine(@RequestBody postMedicineDto: PostMedicineDto) {
        medicinesService.addNew(postMedicineDto)
    }

    @GetMapping("/medicines")
    fun getAllMedicines(): List<GetMedicineDto> {
        return medicinesService.getAll()
    }

    @DeleteMapping("/medicines/{id}")
    fun deleteMedicine(@PathVariable("id") id: Long) {
        medicinesService.delete(id)
    }

    @GetMapping("/medicines/safeByName/{name}")
    fun getMedicinesByNameSafeVersion(@PathVariable("name") name: String): List<GetMedicineDto> {
       return medicinesService.getByNameSafe(name)
    }

    @GetMapping("medicines/unsafeByName/{name}")
    fun getMedicinesByNameUnsafeVersion(@PathVariable("name") name: String): List<GetMedicineDto> {
        return medicinesService.getByNameUnsafe(name)
    }
}