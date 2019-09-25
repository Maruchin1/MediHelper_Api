package com.example.medihelperapi.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class SyncRequestDto<T>(
        @JsonProperty(value = "deleteRemoteIdList")
        val deleteRemoteIdList: List<Long>,

        @JsonProperty(value = "insertUpdateDtoList")
        val insertUpdateDtoList: List<T>
)