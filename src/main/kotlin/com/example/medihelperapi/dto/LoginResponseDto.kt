package com.example.medihelperapi.dto

data class LoginResponseDto(
        val authToken: String,
        val isDataAvailable: Boolean
)