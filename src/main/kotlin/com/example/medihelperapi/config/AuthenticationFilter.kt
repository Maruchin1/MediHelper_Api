package com.example.medihelperapi.config

import com.example.medihelperapi.utils.CookieUtil
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(requiresAuth: RequestMatcher) : AbstractAuthenticationProcessingFilter(requiresAuth) {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        var token = ""
        if (request != null) {
            token = CookieUtil.getValue(request)
        }
        val requestAuthentication = UsernamePasswordAuthenticationToken(token, token)
        return authenticationManager.authenticate(requestAuthentication)
    }

    override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?, authResult: Authentication?) {
        SecurityContextHolder.getContext().authentication = authResult
        chain?.doFilter(request, response)
    }
}