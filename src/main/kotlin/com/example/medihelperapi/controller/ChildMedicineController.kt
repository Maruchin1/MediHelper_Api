package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.PostChildMedicineDto
import com.example.medihelperapi.service.ChildMedicineService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/childMedicines")
class ChildMedicineController(
    private val childMedicineService: ChildMedicineService
) {

    @PostMapping
    fun assignMedicineToChild(@RequestBody dto: PostChildMedicineDto) {
        childMedicineService.assignMedicineToChild(dto)
    }

    @DeleteMapping("/{id}")
    fun deleteChildMedicine(@PathVariable("id") id: Long) {
        childMedicineService.delete(id)
    }
}