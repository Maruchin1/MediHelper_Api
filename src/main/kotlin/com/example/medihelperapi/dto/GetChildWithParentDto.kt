package com.example.medihelperapi.dto

import com.example.medihelperapi.model.Child
import com.fasterxml.jackson.annotation.JsonProperty

data class GetChildWithParentDto(
    @JsonProperty(value = "childId")
    val childId: Long,

    @JsonProperty(value = "name")
    val name: String,

    @JsonProperty(value = "parent")
    val parent: GetParentDto
) {
    constructor(entity: Child) : this(
        childId = entity.childId,
        name = entity.name,
        parent = GetParentDto(entity.parent)
    )
}