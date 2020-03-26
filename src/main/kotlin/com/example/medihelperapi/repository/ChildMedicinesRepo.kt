package com.example.medihelperapi.repository

import com.example.medihelperapi.model.ChildMedicine
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChildMedicinesRepo : JpaRepository<ChildMedicine, Long>