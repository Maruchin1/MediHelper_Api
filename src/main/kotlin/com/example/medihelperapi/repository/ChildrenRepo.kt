package com.example.medihelperapi.repository

import com.example.medihelperapi.model.Child
import com.example.medihelperapi.model.Parent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChildrenRepo : JpaRepository<Child, Long> {

    fun findByConnectionKey(connectionKey: String): Optional<Child>

    fun findByAuthToken(authToken: String): Optional<Child>

    fun findAllByParent(parent: Parent): List<Child>
}