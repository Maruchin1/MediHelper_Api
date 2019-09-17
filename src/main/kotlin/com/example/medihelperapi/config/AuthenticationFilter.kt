package com.example.medihelperapi.config

import org.apache.commons.lang3.StringUtils
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(requiresAuth: RequestMatcher) : AbstractAuthenticationProcessingFilter(requiresAuth) {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val tokenParam = Optional.ofNullable(request?.getHeader(AUTHORIZATION))
        var token = request?.getHeader(AUTHORIZATION)
        if (token != null) {
            token = StringUtils.removeStart(token, "Bearer").trim()
        }
        val requestAuthentication = UsernamePasswordAuthenticationToken(token, token)
        return authenticationManager.authenticate(requestAuthentication)
    }

    override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?, authResult: Authentication?) {
        SecurityContextHolder.getContext().authentication = authResult
        chain?.doFilter(request, response)
    }
}