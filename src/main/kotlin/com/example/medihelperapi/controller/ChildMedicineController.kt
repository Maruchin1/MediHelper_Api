package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.GetMedicineDto
import com.example.medihelperapi.dto.PostChildMedicineDto
import com.example.medihelperapi.service.ChildMedicineService
import org.springframework.web.bind.annotation.*

@RestController
class ChildMedicineController(
    private val childMedicineService: ChildMedicineService
) {

    @PostMapping("/childMedicines")
    fun assignMedicineToChild(@RequestBody dto: PostChildMedicineDto) {
        childMedicineService.assignMedicineToChild(dto)
    }

    @DeleteMapping("/childMedicines/{id}")
    fun deleteChildMedicine(@PathVariable("id") id: Long) {
        childMedicineService.delete(id)
    }

    @GetMapping("/childMedicines")
    fun getChildMedicines(): List<GetMedicineDto> {
        return childMedicineService.getChildMedicines()
    }

    @GetMapping("/childMedicines/{childId}")
    fun getChildMedicinesByParent(@PathVariable("childId") childId: Long): List<GetMedicineDto> {
        return childMedicineService.getChildMedicinesByParent(childId)
    }
}