package com.example.medihelperapi.config

import com.example.medihelperapi.service.UserService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import java.util.*

@Component
class AuthenticationProvider(private val userService: UserService) : AbstractUserDetailsAuthenticationProvider() {

    override fun retrieveUser(username: String?, authentication: UsernamePasswordAuthenticationToken?): UserDetails {
        val token = authentication?.credentials
        return Optional
                .ofNullable(token)
                .map { it.toString() }
                .flatMap { userService.findByToken(it) }
                .orElseThrow {
                    UsernameNotFoundException("Cannot find user with authentication token = $token")
                }
//        return userService.findByToken("").get()
    }

    override fun additionalAuthenticationChecks(userDetails: UserDetails?, authentication: UsernamePasswordAuthenticationToken?) {

    }
}