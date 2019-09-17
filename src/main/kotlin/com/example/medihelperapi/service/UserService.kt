package com.example.medihelperapi.service

import com.example.medihelperapi.model.AppUser
import com.example.medihelperapi.repository.UserRepository
import org.apache.commons.lang3.StringUtils
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
        private val userRepository: UserRepository
) {

    fun login(email: String, password: String): String {
        return userRepository.findByEmailAndPassword(email, password).map { appUser ->
            val token = UUID.randomUUID().toString()
            appUser.token = token
            userRepository.save(appUser)
            token
        }.orElseGet {
            StringUtils.EMPTY
        }
    }

    fun findByToken(token: String): Optional<User> {
        return userRepository.findByToken(token).map { appUser ->
            val user = User(appUser.email, appUser.password, AuthorityUtils.createAuthorityList("USER"))
            Optional.of(user)
        }.orElseGet {
            Optional.empty()
        }
    }

    fun findByID(userID: Long): Optional<AppUser> {
        return userRepository.findById(userID)
    }
}