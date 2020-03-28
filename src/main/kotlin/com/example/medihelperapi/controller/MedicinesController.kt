package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.GetMedicineDto
import com.example.medihelperapi.dto.PostMedicineDto
import com.example.medihelperapi.service.MedicinesService
import org.springframework.web.bind.annotation.*

@RestController
class MedicinesController(
    private val medicinesService: MedicinesService
) {

//    @CrossOrigin(origins = ["http://localhost:4200"])
    @PostMapping("/medicines")
    fun addNewMedicine(@RequestBody postMedicineDto: PostMedicineDto) {
        medicinesService.addNew(postMedicineDto)
    }

//    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping("/medicines")
    fun getAllMedicines(): List<GetMedicineDto> {
        return medicinesService.getAll()
    }

//    @CrossOrigin(origins = ["http://localhost:4200"])
    @DeleteMapping("/medicines/{id}")
    fun deleteMedicine(@PathVariable("id") id: Long) {
        medicinesService.delete(id)
    }
}