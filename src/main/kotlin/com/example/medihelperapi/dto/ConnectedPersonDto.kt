package com.example.medihelperapi.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ConnectedPersonDto(
        @JsonProperty(value = "personRemoteId")
        val personRemoteId: Long,

        @JsonProperty(value = "personName")
        val personName: String,

        @JsonProperty(value = "personColorResId")
        val personColorResId: Int,

        @JsonProperty(value = "authToken")
        val authToken: String
)