package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.PersonGetDto
import com.example.medihelperapi.dto.PersonPostDto
import com.example.medihelperapi.dto.PostResponseDto
import com.example.medihelperapi.getAuthenticatedUserEmail
import com.example.medihelperapi.service.PersonService
import com.example.medihelperapi.service.RegisteredUserService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.security.core.context.SecurityContextHolder
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
    fun overwritePersons(@RequestBody personPostDtoList: List<PersonPostDto>): List<PostResponseDto> {
        val registeredUser = registeredUserService.findByEmail(SecurityContextHolder.getContext().getAuthenticatedUserEmail())
        return personService.overwritePersons(registeredUser, personPostDtoList)
    }

    @GetMapping("/persons")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun getAllPersons(): List<PersonGetDto> {
        val registeredUser = registeredUserService.findByEmail(SecurityContextHolder.getContext().getAuthenticatedUserEmail())
        return personService.getAllPersons(registeredUser)
    }
}