package com.example.medihelperapi.dto

import com.example.medihelperapi.model.Person
import com.fasterxml.jackson.annotation.JsonProperty

data class PersonGetDto(
        @JsonProperty(value = "personId")
        val personId: Long,

        @JsonProperty(value = "personName")
        val personName: String,

        @JsonProperty(value = "personColorResId")
        val personColorResId: Int
) {
        constructor(person: Person) : this(
                personId = person.personId,
                personName = person.personName,
                personColorResId = person.personColorResId
        )
}