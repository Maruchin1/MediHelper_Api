package com.example.medihelperapi.dto.person

import com.example.medihelperapi.model.Person
import com.fasterxml.jackson.annotation.JsonProperty

data class PersonGetDto(
        @JsonProperty(value = "personId")
        val personId: Long,

        @JsonProperty(value = "personName")
        val personName: String,

        @JsonProperty(value = "personColorResId")
        val personColorResId: Int,

        @JsonProperty(value = "mainPerson")
        val mainPerson: Boolean
) {
    constructor(person: Person) : this(
            personId = person.personId,
            personName = person.personName,
            mainPerson = person.mainPerson,
            personColorResId = person.personColorResId
    )
}