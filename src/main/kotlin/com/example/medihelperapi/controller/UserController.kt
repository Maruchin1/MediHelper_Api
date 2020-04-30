package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.LoginChildDto
import com.example.medihelperapi.dto.LoginParentDto
import com.example.medihelperapi.dto.RegisterParentDto
import com.example.medihelperapi.service.UserService
import com.example.medihelperapi.utils.CookieUtil
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
class UserController(
    private val userService: UserService
) {

    @PostMapping("/users/register-parent")
    fun registerParent(
        httpServletResponse: HttpServletResponse,
        @RequestBody registerParentDto: RegisterParentDto
    ) {
        val token = userService.register(registerParentDto)
        CookieUtil.create(httpServletResponse, token)
    }

    @PostMapping("/users/login-parent")
    fun loginParent(
        httpServletResponse: HttpServletResponse,
        @RequestBody loginParentDto: LoginParentDto
    ) {
        val token = userService.login(loginParentDto)
        CookieUtil.create(httpServletResponse, token)
    }

    @PostMapping("/users/login-child")
    fun loginChild(
        httpServletResponse: HttpServletResponse,
        @RequestBody loginChildDto: LoginChildDto
    ) {
        val token = userService.login(loginChildDto)
        CookieUtil.create(httpServletResponse, token)
    }

    @PostMapping("/users/logout")
    fun logout(httpServletResponse: HttpServletResponse) {
        CookieUtil.clear(httpServletResponse)
    }

    @GetMapping("/users/role")
    fun getUserRole(httpServletResponse: HttpServletResponse): String {
        return userService.getRole().toString()
    }

}