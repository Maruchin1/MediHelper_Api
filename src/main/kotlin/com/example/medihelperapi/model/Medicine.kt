package com.example.medihelperapi.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "medicines")
data class Medicine(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val medicineId: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    var parent: Parent,

    var name: String,

    var unit: String,

    var expireDate: LocalDate?,

    var packageSize: Float?,

    var currState: Float?
)