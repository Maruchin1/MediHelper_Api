package com.example.medihelperapi.model

import javax.persistence.*

@Entity
@Table(name = "days_of_week")
data class DaysOfWeek(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var daysOfWeekId: Long = 0,

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "daysOfWeek")
        var medicinePlan: MedicinePlan? = null,

        var monday: Boolean,

        var tuesday: Boolean,

        var wednesday: Boolean,

        var thursday: Boolean,

        var friday: Boolean,

        var saturday: Boolean,

        var sunday: Boolean
)