package com.example.medihelperapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime
import javax.persistence.*


@Entity
@Table(name = "persons")
data class Person(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val personId: Long = 0,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "registeredUserId")
        @OnDelete(action = OnDeleteAction.CASCADE)
        var registeredUser: RegisteredUser,

        var personName: String,

        var personColorResId: Int,

        var isMainPerson: Boolean,

        var lastModificationTime: LocalDateTime
)