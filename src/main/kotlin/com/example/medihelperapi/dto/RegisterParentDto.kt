package com.example.medihelperapi.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterParentDto(
    @JsonProperty("name")
    val name: String,

    @JsonProperty("email")
    val email: String,

    @JsonProperty("password")
    val password: String
)