package com.example.medihelperapi.dto

import com.example.medihelperapi.model.Child
import com.example.medihelperapi.model.Parent
import com.fasterxml.jackson.annotation.JsonProperty

data class PostChildDto(
    @JsonProperty(value = "personName")
    val name: String
) {
    fun toEntity(parent: Parent) = Child(
        parent = parent,
        name = name
    )
}