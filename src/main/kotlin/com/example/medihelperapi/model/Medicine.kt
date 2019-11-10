package com.example.medihelperapi.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "medicines")
data class Medicine(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val medicineId: Long,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "registeredUserId")
        @OnDelete(action = OnDeleteAction.CASCADE)
        var registeredUser: RegisteredUser,

        var medicineName: String,

        var medicineUnit: String,

        var expireDate: LocalDate?,

        var packageSize: Float?,

        var currState: Float?,

        var additionalInfo: String?,

        var imageName: String?
) {
    @OneToMany(mappedBy = "medicine")
    @OnDelete(action = OnDeleteAction.CASCADE)
    var medicinePlanList: List<MedicinePlan> = emptyList()
}