package com.example.medihelperapi.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*


@Entity
@Table(name = "children")
data class Child(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val childId: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    var parent: Parent,

    var name: String,

    var authToken: String = "",

    var connectionKey: String = ""
)