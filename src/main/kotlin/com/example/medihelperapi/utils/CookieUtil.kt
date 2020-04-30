package com.example.medihelperapi.utils

import org.springframework.web.util.WebUtils
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

object CookieUtil {

    private const val COOKIE_NAME = "JWT-TOKEN"
    private const val COOKIE_PATH = "/"
//    private const val COOKIE_DOMAIN = "localhost"
    private const val COOKIE_DOMAIN = "medihelper-api.herokuapp.com"

    fun create(httpServletResponse: HttpServletResponse, value: String) {
        val cookie = Cookie(COOKIE_NAME, value).apply {
            secure = false
            isHttpOnly = true
            maxAge = -1
            domain = COOKIE_DOMAIN
            path = COOKIE_PATH
        }
        httpServletResponse.addCookie(cookie)
    }

    fun clear(httpServletResponse: HttpServletResponse) {
        val cookie = Cookie(COOKIE_NAME, "").apply {
            isHttpOnly = true
            maxAge = 0
            domain = COOKIE_DOMAIN
            path = COOKIE_PATH
        }
        httpServletResponse.addCookie(cookie)
    }

    fun getValue(httpServletRequest: HttpServletRequest): String {
        val cookie = WebUtils.getCookie(httpServletRequest, COOKIE_NAME)
        return cookie?.value ?: ""
    }
}