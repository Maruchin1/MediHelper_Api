package com.example.medihelperapi.dto


data class UserRegistrationDto(
        val email: String,
        val password: String,
        val passwordConfirmation: String
)