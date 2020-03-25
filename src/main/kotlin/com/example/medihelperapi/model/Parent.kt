package com.example.medihelperapi.model

import javax.persistence.*

@Entity
@Table(name = "parents")
data class Parent(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val parentId: Long = 0,

    var userName: String,

    var email: String,

    var password: String,

    var authToken: String = ""
)