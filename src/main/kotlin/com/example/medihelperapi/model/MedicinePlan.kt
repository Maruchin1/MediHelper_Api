package com.example.medihelperapi.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "medicines_plans")
data class MedicinePlan(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val medicinePlanId: Long,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "medicineId")
        @OnDelete(action = OnDeleteAction.CASCADE)
        var medicine: Medicine,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "personId")
        @OnDelete(action = OnDeleteAction.CASCADE)
        var person: Person?,

        var startDate: LocalDate,

        var endDate: LocalDate?,

        var durationType: String,

        var intervalOfDays: Int?,

        var daysType: String?
) {
        @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
        var daysOfWeek: DaysOfWeek? = null

        @OneToMany(mappedBy = "medicinePlan", cascade = [CascadeType.ALL], orphanRemoval = true)
        var timeDoseList: List<TimeDose> = emptyList()
}