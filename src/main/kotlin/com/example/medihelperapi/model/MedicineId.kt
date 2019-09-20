package com.example.medihelperapi.model

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class MedicineId(
        @Column(name = "registered_user_id")
        var registeredUserID: Long,

        @Column(name = "medicine_name")
        var medicineName: String
) : Serializable