package com.example.medihelperapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
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
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(private val authenticationProvider: AuthenticationProvider) : WebSecurityConfigurerAdapter() {

    private val parentAuth: String
        get() = UserRole.PARENT.toString()

    private val childAuth: String
        get() = UserRole.CHILD.toString()

    private val guestAuth: String
        get() = UserRole.GUEST.toString()

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.authenticationProvider(authenticationProvider)
    }

    override fun configure(web: WebSecurity?) {
        web?.ignoring()?.antMatchers(

        )
    }

    override fun configure(http: HttpSecurity?) {
        if (http != null) {
            http.cors().configurationSource(corsConfigurationSource())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter(), BasicAuthenticationFilter::class.java)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/childMedicines").hasAuthority(childAuth)
                .antMatchers("/childMedicines/**").hasAuthority(parentAuth)
                .antMatchers("/children/byAuthToken").hasAuthority(childAuth)
                .antMatchers("/children/**").hasAuthority(parentAuth)
                .antMatchers("/medicines/**").hasAuthority(parentAuth)
                .antMatchers("/parents/**").hasAuthority(parentAuth)
                .antMatchers("/users/role").authenticated()
                .antMatchers("/users/logout").authenticated()
                .antMatchers("/users/**").hasAuthority(guestAuth)
                .and()
                .csrf()
                .disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable()

        }
    }

    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration().apply {
            allowedOrigins = listOf(
                "http://localhost:4200",
                "http://localhost:4201",
                "https://demo-web-security.herokuapp.com"
            )
            allowedMethods = listOf("*")
            allowCredentials = true
            addAllowedOrigin("http://localhost:4200")
            addAllowedOrigin("http://localhost:4201")
            addAllowedOrigin("https://demo-web-security.herokuapp.com")
            addAllowedHeader("*")
            addAllowedMethod("*")
        }
        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", config)
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

    private fun authenticationFilter() = AuthenticationFilter(
        OrRequestMatcher(
            AntPathRequestMatcher("/childMedicines/**"),
            AntPathRequestMatcher("/children/**"),
            AntPathRequestMatcher("/medicines/**"),
            AntPathRequestMatcher("/parents/**"),
            AntPathRequestMatcher("/users/**")
        )
    ).apply {
        setAuthenticationManager(authenticationManager())
    }
}