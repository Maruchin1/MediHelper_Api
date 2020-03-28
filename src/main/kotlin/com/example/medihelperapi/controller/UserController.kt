package com.example.medihelperapi.controller

import com.example.medihelperapi.config.UserRole
import com.example.medihelperapi.service.CurrUserService
import com.example.medihelperapi.service.Forbidden
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
        private val currUserService: CurrUserService
) {

    @GetMapping("/{token}")
    fun getUserRole(@PathVariable("token") id: String): String {

        try {
            currUserService.expectParent()
            return UserRole.PARENT.toString()
        } catch (e: Forbidden){}

        try {
            currUserService.expectChild()
            return UserRole.CHILD.toString()
        } catch (e: Forbidden) {}

        return UserRole.GUEST.toString()
    }

}