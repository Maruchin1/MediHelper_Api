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
        var person: Person,

        var startDate: LocalDate,

        var endDate: LocalDate?,

        var durationType: String,

        @Embedded
        var daysOfWeek: DaysOfWeek?,

        var intervalOfDays: Int?,

        var daysType: String,

        @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "medicinePlanId")
        @OnDelete(action = OnDeleteAction.CASCADE)
        var timeOfTakingList: List<TimeOfTaking>,

        @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "medicinePlanId")
        @OnDelete(action = OnDeleteAction.CASCADE)
        var plannedMedicineList: List<PlannedMedicine>
) {
    @Embeddable
    class DaysOfWeek(
          var monday: Boolean?,
          var tuesday: Boolean?,
          var wednesday: Boolean?,
          var thursday: Boolean?,
          var friday: Boolean?,
          var saturday: Boolean?,
          var sunday: Boolean?
    )
}