package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.PersonProfileDataDto
import com.example.medihelperapi.service.PersonService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/persons")
class PersonController(private val personService: PersonService) {

    @GetMapping("/profile-data")
    fun getAuthToken(@RequestParam connectionKey: String): PersonProfileDataDto {
        return personService.getProfileData(connectionKey)
    }
}