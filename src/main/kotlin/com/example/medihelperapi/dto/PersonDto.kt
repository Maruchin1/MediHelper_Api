package com.example.medihelperapi.dto

import com.example.medihelperapi.model.Person
import com.example.medihelperapi.model.RegisteredUser
import com.fasterxml.jackson.annotation.JsonProperty

data class PersonDto(
        @JsonProperty(value = "personLocalId")
        val personLocalId: Int?,

        @JsonProperty(value = "personRemoteId")
        val personRemoteId: Long?,

        @JsonProperty(value = "personName")
        val personName: String,

        @JsonProperty(value = "personColorResId")
        val personColorResId: Int,

        @JsonProperty(value = "mainPerson")
        val mainPerson: Boolean
) {
    constructor(person: Person, personLocalId: Int?) : this(
            personLocalId = personLocalId,
            personRemoteId = person.personId,
            personName = person.personName,
            personColorResId = person.personColorResId,
            mainPerson = person.mainPerson
    )

    fun toEntity(registeredUser: RegisteredUser) = Person(
            personId = personRemoteId ?: 0,
            registeredUser = registeredUser,
            personName = personName,
            personColorResId = personColorResId,
            mainPerson = mainPerson
    )
}