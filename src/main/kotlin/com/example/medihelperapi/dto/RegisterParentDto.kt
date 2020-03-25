package com.example.medihelperapi.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterParentDto(
    @JsonProperty("userName")
    val userName: String,

    @JsonProperty("email")
    val email: String,

    @JsonProperty("password")
    val password: String
)