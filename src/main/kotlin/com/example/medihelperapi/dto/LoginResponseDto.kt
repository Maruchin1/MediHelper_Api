package com.example.medihelperapi.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginResponseDto(
        @JsonProperty("userName")
        val userName: String,

        @JsonProperty("authToken")
        val authToken: String,

        @JsonProperty("isDataAvailable")
        val isDataAvailable: Boolean
)