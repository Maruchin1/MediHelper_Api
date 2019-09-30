package com.example.medihelperapi.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*


@Entity
@Table(name = "persons")
data class Person(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val personId: Long,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "registeredUserId")
        @OnDelete(action = OnDeleteAction.CASCADE)
        var registeredUser: RegisteredUser,

        var personName: String,

        var personColorResId: Int,

        var authToken: String = "",

        var tempKey: String = ""
)