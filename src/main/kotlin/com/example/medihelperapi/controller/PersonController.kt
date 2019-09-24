package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.person.PersonGetDto
import com.example.medihelperapi.dto.person.PersonPostDto
import com.example.medihelperapi.dto.PostResponseDto
import com.example.medihelperapi.service.PersonService
import com.example.medihelperapi.service.RegisteredUserService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PersonController(
        private val personService: PersonService,
        private val registeredUserService: RegisteredUserService
) {

    @PostMapping("/persons/overwrite")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun overwritePersons(@RequestBody postDtoList: List<PersonPostDto>): List<PostResponseDto> {
        return personService.overwritePersons(registeredUserService.getLoggedUser(), postDtoList)
    }

    @GetMapping("/persons")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun getAllPersons(): List<PersonGetDto> {
        return personService.getAllPersons(registeredUserService.getLoggedUser())
    }
}