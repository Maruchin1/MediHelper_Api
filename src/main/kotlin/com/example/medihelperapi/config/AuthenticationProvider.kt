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
        //todo role powinny być w jakimś enumie, trzeba też dostosować konfigurację do nich
        val authToken = authentication?.credentials as String
        if (authToken.isEmpty()) {
            throw BadCredentialsException("No Authentication token passed in request")
        }
        val parent = parentsRepo.findByAuthToken(authToken)
        if (parent.isPresent) {
            val id = parent.get().parentId.toString()
            val role = "PARENT"
            return createUser(id, role)
        }
        val child = childrenRepo.findByAuthToken(authToken)
        if (child.isPresent) {
            val id = child.get().childId.toString()
            val role = "CHILD"
            return createUser(id, role)
        }
        throw UsernameNotFoundException("Cannot find user with authentication token = $authToken")
    }

    override fun additionalAuthenticationChecks(userDetails: UserDetails?, authentication: UsernamePasswordAuthenticationToken?) {

    }

    private fun createUser(id: String, role: String): User {
        val authList = AuthorityUtils.createAuthorityList(role)
        return User(id, "", authList)
    }
}