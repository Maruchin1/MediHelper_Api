package com.example.medihelperapi.config

import com.example.medihelperapi.repository.PersonRepository
import com.example.medihelperapi.repository.RegisteredUserRepository
import com.example.medihelperapi.service.RegisteredUserService
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*


@Component
class AuthenticationProvider(
        private val registeredUserRepository: RegisteredUserRepository,
        private val personRepository: PersonRepository
) : AbstractUserDetailsAuthenticationProvider() {

    override fun retrieveUser(username: String?, authentication: UsernamePasswordAuthenticationToken?): UserDetails {
        val token = authentication?.credentials
                ?: throw BadCredentialsException("No Authentication token passed in request")
        return registeredUserRepository.findByAuthToken(token as String).map { registeredUser ->
            User(registeredUser.email, registeredUser.password, AuthorityUtils.createAuthorityList("USER"))
        }.or {
            personRepository.findByAuthToken(token as String).map { person ->
                User(person.personId.toString(), person.connectionKey, AuthorityUtils.createAuthorityList("PERSON"))
            }
        }.orElseThrow {
            UsernameNotFoundException("Cannot find user with authentication token = $token")
        }
    }

    override fun additionalAuthenticationChecks(userDetails: UserDetails?, authentication: UsernamePasswordAuthenticationToken?) {

    }
}