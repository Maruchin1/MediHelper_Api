package com.example.medihelperapi.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class LocalIdRemoteIdPair(
        @JsonProperty(value = "localId")
        val localId: Int,

        @JsonProperty(value = "remoteId")
        val remoteId: Long
)