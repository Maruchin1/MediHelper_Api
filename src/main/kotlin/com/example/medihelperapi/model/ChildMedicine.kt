package com.example.medihelperapi.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
@Table(name = "child_medicines")
data class ChildMedicine(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val assignedMedicineId: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "childId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    var child: Child,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicineId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    var medicine: Medicine
)