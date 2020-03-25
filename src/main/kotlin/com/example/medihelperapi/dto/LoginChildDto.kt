package com.example.medihelperapi.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginChildDto(
    @JsonProperty("connectionKey")
    val connectionKey: String
)