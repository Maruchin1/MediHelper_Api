package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.PostChildMedicineDto
import com.example.medihelperapi.service.ChildMedicineService
import org.springframework.web.bind.annotation.*

@RestController
class ChildMedicineController(
    private val childMedicineService: ChildMedicineService
) {

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PostMapping("/childMedicines")
    fun assignMedicineToChild(@RequestBody dto: PostChildMedicineDto) {
        childMedicineService.assignMedicineToChild(dto)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @DeleteMapping("/childMedicines/{id}")
    fun deleteChildMedicine(@PathVariable("id") id: Long) {
        childMedicineService.delete(id)
    }
}