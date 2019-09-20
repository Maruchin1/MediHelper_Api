package com.example.medihelperapi.repository

import com.example.medihelperapi.model.Medicine
import com.example.medihelperapi.model.MedicineId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MedicineRepository : JpaRepository<Medicine, MedicineId> {
}