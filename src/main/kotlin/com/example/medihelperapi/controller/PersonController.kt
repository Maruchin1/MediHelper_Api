package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.PersonDto
import com.example.medihelperapi.dto.person.PersonGetDto
import com.example.medihelperapi.dto.person.PersonPostDto
import com.example.medihelperapi.dto.PostResponseDto
import com.example.medihelperapi.dto.SyncRequestDto
import com.example.medihelperapi.service.PersonService
import com.example.medihelperapi.service.RegisteredUserService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.web.bind.annotation.*

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

    @PutMapping("/persons/synchronize")
    @ApiImplicitParams(ApiImplicitParam(name = "Authorization", value = "token autoryzacji", required = true, paramType = "header"))
    fun synchronizePersons(@RequestBody syncRequestDto: SyncRequestDto<PersonDto>): List<PersonDto> {
        return personService.synchronizePersons(registeredUserService.getLoggedUser(), syncRequestDto)
    }
}