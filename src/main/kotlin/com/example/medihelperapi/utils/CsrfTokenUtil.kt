package com.example.medihelperapi.utils

import com.example.medihelperapi.model.CsrfToken
import com.example.medihelperapi.repository.CsrfTokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest


@Component
class CsrfTokenUtil @Autowired constructor(repo: CsrfTokenRepository) {

    init {
        tokenRepo = repo
    }

    companion object {
        private var tokenRepo: CsrfTokenRepository? = null

        fun generateToken(): String {
            val newToken = CsrfToken(value = UUID.randomUUID().toString())
            tokenRepo!!.save(newToken)
            return newToken.value
        }

        fun isRequestValid(httpServletRequest: HttpServletRequest): Boolean {
            val secureMethods = arrayOf("post", "put", "delete")
            if (httpServletRequest.method.toLowerCase() !in secureMethods) {
                return true
            }
            val token = httpServletRequest.getHeader("CSRF-TOKEN") ?: ""
            return tokenRepo!!.existsById(token)
        }
    }
}