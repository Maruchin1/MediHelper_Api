package com.example.medihelperapi.repository

import com.example.medihelperapi.model.Medicine
import com.example.medihelperapi.model.Parent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MedicinesRepo : JpaRepository<Medicine, Long> {

    fun findAllByParent(parent: Parent): List<Medicine>
}