package com.example.medihelperapi.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginInputDto(
        @JsonProperty("email")
        val email: String,

        @JsonProperty("password")
        val password: String
)