package com.example.medihelperapi.controller

import com.example.medihelperapi.service.PersonService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/persons")
class PersonController(private val personService: PersonService) {

    @GetMapping("/auth-token")
    fun getAuthToken(@RequestParam connectionKey: String): String {
        return personService.getAuthToken(connectionKey)
    }
}