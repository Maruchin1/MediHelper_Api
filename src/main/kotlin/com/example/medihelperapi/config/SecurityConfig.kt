package com.example.medihelperapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(private val authenticationProvider: AuthenticationProvider) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.authenticationProvider(authenticationProvider)
    }

    override fun configure(web: WebSecurity?) {
        web?.ignoring()?.antMatchers(
                "/registered-users/register",
                "/registered-users/login"
        )
    }

    override fun configure(http: HttpSecurity?) {
        if (http != null) {
            http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .exceptionHandling()
                    .and()
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(authenticationFilter(), BasicAuthenticationFilter::class.java)
                    .authorizeRequests()
                    .requestMatchers(PROTECTED_URLS).hasAuthority("USER")
                    .and()
                    .csrf().disable()
                    .formLogin().disable()
                    .httpBasic().disable()
                    .logout().disable()

        }
    }

    @Bean
    fun forbiddenEntryPoint(): AuthenticationEntryPoint {
        return HttpStatusEntryPoint(HttpStatus.FORBIDDEN)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    private fun authenticationFilter() = AuthenticationFilter(PROTECTED_URLS).apply {
        setAuthenticationManager(authenticationManager())
    }

    companion object {
        private val PROTECTED_URLS = OrRequestMatcher(
                AntPathRequestMatcher("/registered-users/**")
        )
    }
}