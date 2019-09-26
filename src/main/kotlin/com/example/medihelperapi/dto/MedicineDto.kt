package com.example.medihelperapi.dto

import com.example.medihelperapi.model.Medicine
import com.example.medihelperapi.model.RegisteredUser
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class MedicineDto(
        @JsonProperty(value = "medicineRemoteId")
        val medicineRemoteId: Long?,

        @JsonProperty(value = "medicineName")
        val medicineName: String,

        @JsonProperty(value = "medicineUnit")
        val medicineUnit: String,

        @JsonProperty(value = "expireDate")
        @JsonFormat(pattern = "dd-MM-yyyy")
        val expireDate: LocalDate?,

        @JsonProperty(value = "packageSize")
        val packageSize: Float?,

        @JsonProperty(value = "currState")
        val currState: Float?,

        @JsonProperty(value = "additionalInfo")
        val additionalInfo: String?,

        @JsonProperty(value = "image")
        val image: ByteArray?
) {
    constructor(medicine: Medicine) : this(
            medicineRemoteId = medicine.medicineId,
            medicineName = medicine.medicineName,
            medicineUnit = medicine.medicineUnit,
            expireDate = medicine.expireDate,
            packageSize = medicine.packageSize,
            currState = medicine.currState,
            additionalInfo = medicine.additionalInfo,
            image = medicine.image
    )

    fun toEntity(registeredUser: RegisteredUser) = Medicine(
            medicineId = medicineRemoteId ?: 0,
            registeredUser = registeredUser,
            medicineName = medicineName,
            medicineUnit = medicineUnit,
            expireDate = expireDate,
            packageSize = packageSize,
            currState = currState,
            additionalInfo = additionalInfo,
            image = image
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MedicineDto

        if (medicineRemoteId != other.medicineRemoteId) return false
        if (medicineName != other.medicineName) return false
        if (medicineUnit != other.medicineUnit) return false
        if (expireDate != other.expireDate) return false
        if (packageSize != other.packageSize) return false
        if (currState != other.currState) return false
        if (additionalInfo != other.additionalInfo) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = medicineRemoteId?.hashCode() ?: 0
        result = 31 * result + medicineName.hashCode()
        result = 31 * result + medicineUnit.hashCode()
        result = 31 * result + (expireDate?.hashCode() ?: 0)
        result = 31 * result + (packageSize?.hashCode() ?: 0)
        result = 31 * result + (currState?.hashCode() ?: 0)
        result = 31 * result + (additionalInfo?.hashCode() ?: 0)
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }
}