package com.example.medihelperapi.utils

import com.example.medihelperapi.model.CsrfToken
import com.example.medihelperapi.repository.CsrfTokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import java.util.prefs.Preferences
import javax.servlet.http.HttpServletRequest


@Component
class CsrfTokenUtil @Autowired constructor(repo: CsrfTokenRepository) {

    init {
        tokenRepo = repo
    }

    companion object {
        private const val KEY_ENABLED = "csrf-enabled"
        private var tokenRepo: CsrfTokenRepository? = null

        fun isCsrfEnabled(): Boolean {
            val prefs = Preferences.userRoot()
            return prefs.getBoolean(KEY_ENABLED, false)
        }

        fun switchCsrfEnabled(enabled: Boolean) {
            println("enabled: $enabled")
            val prefs = Preferences.userRoot()
            prefs.putBoolean(KEY_ENABLED, enabled)
        }

        fun generateToken(): String {
            val newToken = CsrfToken(value = UUID.randomUUID().toString())
            tokenRepo!!.save(newToken)
            return newToken.value
        }

        fun isRequestValid(httpServletRequest: HttpServletRequest): Boolean {
            val secureMethods = arrayOf("post", "put", "delete")
            if (!isCsrfEnabled() || httpServletRequest.method.toLowerCase() !in secureMethods) {
                return true
            }
            val token = httpServletRequest.getHeader("CSRF-TOKEN") ?: ""
            return tokenRepo!!.existsById(token)
        }
    }
}