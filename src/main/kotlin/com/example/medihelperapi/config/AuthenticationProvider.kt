package com.example.medihelperapi.config

import com.example.medihelperapi.repository.ChildrenRepo
import com.example.medihelperapi.repository.ParentsRepo
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component


@Component
class AuthenticationProvider(
    private val parentsRepo: ParentsRepo,
    private val childrenRepo: ChildrenRepo
) : AbstractUserDetailsAuthenticationProvider() {

    override fun retrieveUser(username: String?, authentication: UsernamePasswordAuthenticationToken?): UserDetails {
        if (authentication?.credentials is String) {
            val authToken = authentication.credentials as String
            val parent = parentsRepo.findByAuthToken(authToken)
            if (parent.isPresent) {
                return createUser(authToken, UserRole.PARENT)
            }
            val child = childrenRepo.findByAuthToken(authToken)
            if (child.isPresent) {
                return createUser(authToken, UserRole.CHILD)
            }
        }
        return createUser("guest", UserRole.GUEST)
    }

    override fun additionalAuthenticationChecks(userDetails: UserDetails?, authentication: UsernamePasswordAuthenticationToken?) {

    }

    private fun createUser(id: String, role: UserRole): User {
        val authList = AuthorityUtils.createAuthorityList(role.toString())
        return User(id, "", authList)
    }
}