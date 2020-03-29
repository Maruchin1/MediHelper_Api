package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.GetMedicineDto
import com.example.medihelperapi.dto.PostMedicineDto
import com.example.medihelperapi.service.MedicinesService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/medicines")
class MedicinesController(
    private val medicinesService: MedicinesService
) {

    @PostMapping
    fun addNewMedicine(@RequestBody postMedicineDto: PostMedicineDto) {
        medicinesService.addNew(postMedicineDto)
    }

    @GetMapping
    fun getAllMedicines(): List<GetMedicineDto> {
        return medicinesService.getAll()
    }

    @DeleteMapping("/{id}")
    fun deleteMedicine(@PathVariable("id") id: Long) {
        medicinesService.delete(id)
    }
}