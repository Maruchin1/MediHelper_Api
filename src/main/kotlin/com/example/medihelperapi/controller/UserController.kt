package com.example.medihelperapi.controller

import com.example.medihelperapi.dto.LoginChildDto
import com.example.medihelperapi.dto.LoginParentDto
import com.example.medihelperapi.dto.RegisterParentDto
import com.example.medihelperapi.service.UserService
import com.example.medihelperapi.utils.CookieUtil
import com.example.medihelperapi.utils.CsrfTokenUtil
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
    ): String {
        val token = userService.register(registerParentDto)
        CookieUtil.create(httpServletResponse, token)
        return CsrfTokenUtil.generateToken()
    }

    @PostMapping("/users/login-parent")
    fun loginParent(
        httpServletResponse: HttpServletResponse,
        @RequestBody loginParentDto: LoginParentDto
    ): String {
        val token = userService.login(loginParentDto)
        CookieUtil.create(httpServletResponse, token)
        return CsrfTokenUtil.generateToken()
    }

    @PostMapping("/users/login-child")
    fun loginChild(
        httpServletResponse: HttpServletResponse,
        @RequestBody loginChildDto: LoginChildDto
    ): String {
        val token = userService.login(loginChildDto)
        CookieUtil.create(httpServletResponse, token)
        return CsrfTokenUtil.generateToken()
    }

    @PostMapping("/users/logout")
    fun logout(httpServletResponse: HttpServletResponse) {
        CookieUtil.clear(httpServletResponse)
    }

    @GetMapping("/users/role")
    fun getUserRole(httpServletResponse: HttpServletResponse): String {
        return userService.getRole().toString()
    }

    @GetMapping("/users/csrf")
    fun getCsrfTokenEnabled(): Boolean {
        return CsrfTokenUtil.isCsrfEnabled()
    }

    @PostMapping("/users/csrf/{enabled}")
    fun switchCsrfToken(@PathVariable("enabled") enabled: Boolean) {
        CsrfTokenUtil.switchCsrfEnabled(enabled)
    }
}