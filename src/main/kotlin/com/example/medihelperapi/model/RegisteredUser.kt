package com.example.medihelperapi.model

import javax.persistence.*

@Entity
@Table(name = "registered_user")
data class RegisteredUser(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val registeredUserID: Long = 0,

        var email: String,

        var password: String,

        var authToken: String = ""
)