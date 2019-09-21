package com.example.medihelperapi.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class PostResponseDto(
        @JsonProperty(value = "localId")
        val localId: Int,

        @JsonProperty(value = "remoteId")
        val remoteId: Long
)