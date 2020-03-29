package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.*
import com.example.medihelperapi.service.ChildrenService
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:4200"], maxAge = 3600)
@RestController
class ChildrenController(
    private val childrenService: ChildrenService
) {

    @PostMapping("/children/login")
    fun loginChild(@RequestBody loginChildDto: LoginChildDto): String {
        return childrenService.login(loginChildDto)
    }

    @PostMapping("/children")
    fun addNewChild(@RequestBody postChildDto: PostChildDto) {
        childrenService.addNew(postChildDto)
    }

    @GetMapping("/children")
    fun getAllChildren(): List<GetChildDto> {
        return childrenService.getAll()
    }

    @GetMapping("/children/byAuthToken")
    fun getLoggedChildren(@RequestHeader(name = "Authorization") authToken: String): GetChildWithParentDto {
        return childrenService.getChildAndParentPair(authToken)
    }

    @DeleteMapping("/children/{id}")
    fun deleteChild(@PathVariable("id") id: Long) {
        childrenService.delete(id)
    }
}