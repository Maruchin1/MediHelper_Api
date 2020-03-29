package com.example.medihelperapi.dto

import com.example.medihelperapi.model.Parent
import com.fasterxml.jackson.annotation.JsonProperty

data class GetParentDto(
    @JsonProperty(value = "parentId")
    val parentId: Long,

    @JsonProperty(value = "name")
    val name: String,

    @JsonProperty(value = "email")
    val email: String
) {
    constructor(entity: Parent) : this(
        parentId = entity.parentId,
        name = entity.userName,
        email = entity.email
    )
}