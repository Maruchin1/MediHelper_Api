package com.example.medihelperapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "medicines")
data class Medicine(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val medicineId: Long = 0,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "registeredUserId")
        @OnDelete(action = OnDeleteAction.CASCADE)
        @JsonIgnore
        var registeredUser: RegisteredUser,

        var medicineName: String,

        var medicineUnit: String,

        var expireDate: LocalDate?,

        var packageSize: Float?,

        var currState: Float?,

        var additionalInfo: String?,

        var lastModificationTime: LocalDateTime
)