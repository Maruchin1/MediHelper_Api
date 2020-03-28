package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.GetChildDto
import com.example.medihelperapi.dto.LoginChildDto
import com.example.medihelperapi.dto.PostChildDto
import com.example.medihelperapi.service.ChildrenService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/children")
class ChildrenController(
    private val childrenService: ChildrenService
) {

    @PostMapping("/login")
    fun loginChild(@RequestBody loginChildDto: LoginChildDto): String {
        return childrenService.login(loginChildDto)
    }

    @PostMapping
    fun addNewChild(@RequestBody postChildDto: PostChildDto) {
        childrenService.addNew(postChildDto)
    }

    @GetMapping
    fun getAllChildren(): List<GetChildDto> {
        return childrenService.getAll()
    }

    @DeleteMapping("/children/{id}")
    fun deleteChild(@PathVariable("id") id: Long) {
        childrenService.delete(id)
    }
}