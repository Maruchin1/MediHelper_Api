package com.example.medihelperapi.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginParentDto(
    @JsonProperty("email")
    val email: String,

    @JsonProperty("password")
    val password: String
)