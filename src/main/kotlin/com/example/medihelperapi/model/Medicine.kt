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

        var image: ByteArray?,

        var lastModificationTime: LocalDateTime
) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Medicine

                if (medicineId != other.medicineId) return false
                if (registeredUser != other.registeredUser) return false
                if (medicineName != other.medicineName) return false
                if (medicineUnit != other.medicineUnit) return false
                if (expireDate != other.expireDate) return false
                if (packageSize != other.packageSize) return false
                if (currState != other.currState) return false
                if (additionalInfo != other.additionalInfo) return false
                if (image != null) {
                        if (other.image == null) return false
                        if (!image!!.contentEquals(other.image!!)) return false
                } else if (other.image != null) return false
                if (lastModificationTime != other.lastModificationTime) return false

                return true
        }

        override fun hashCode(): Int {
                var result = medicineId.hashCode()
                result = 31 * result + registeredUser.hashCode()
                result = 31 * result + medicineName.hashCode()
                result = 31 * result + medicineUnit.hashCode()
                result = 31 * result + (expireDate?.hashCode() ?: 0)
                result = 31 * result + (packageSize?.hashCode() ?: 0)
                result = 31 * result + (currState?.hashCode() ?: 0)
                result = 31 * result + (additionalInfo?.hashCode() ?: 0)
                result = 31 * result + (image?.contentHashCode() ?: 0)
                result = 31 * result + lastModificationTime.hashCode()
                return result
        }
}