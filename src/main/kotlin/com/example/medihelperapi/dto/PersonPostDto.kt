package com.example.medihelperapi.dto

import com.example.medihelperapi.model.Person
import com.example.medihelperapi.model.RegisteredUser
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class PersonPostDto(
        @JsonProperty(value = "personLocalId")
        val personLocalId: Int,

        @JsonProperty(value = "personName")
        val personName: String,

        @JsonProperty(value = "personColorResId")
        val personColorResId: Int,

        @JsonProperty(value = "isMainPerson")
        val isMainPerson: Boolean,

        @JsonProperty(value = "operationTime")
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        val operationTime: LocalDateTime
) {
        fun toPersonEntity(registeredUser: RegisteredUser) = Person(
                registeredUser = registeredUser,
                personName = personName,
                personColorResId = personColorResId,
                isMainPerson = isMainPerson,
                lastModificationTime = operationTime
        )
}