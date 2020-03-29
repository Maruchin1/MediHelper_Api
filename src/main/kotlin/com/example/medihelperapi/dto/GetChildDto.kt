package com.example.medihelperapi.dto

import com.example.medihelperapi.model.Child
import com.fasterxml.jackson.annotation.JsonProperty

data class GetChildDto(
    @JsonProperty(value = "childId")
    val childId: Long,

    @JsonProperty(value = "name")
    val name: String,

    @JsonProperty(value = "connectionKey")
    val connectionKey: String
) {
    constructor(entity: Child) : this(
        childId = entity.childId,
        name = entity.name,
        connectionKey = entity.connectionKey
    )
}