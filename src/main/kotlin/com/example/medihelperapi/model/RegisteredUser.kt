package com.example.medihelperapi.model

import javax.persistence.*

@Entity
@Table(name = "registered_user")
data class RegisteredUser(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "registered_user_id")
        val registeredUserId: Long = 0,

        var email: String,

        var password: String,

        var authToken: String = ""
)