package com.example.medihelperapi.model

import javax.persistence.*

@Entity
@Table(name = "app_user")
data class AppUser(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val userID: Long = 0,

        var email: String,

        var password: String,

        var token: String = ""
)