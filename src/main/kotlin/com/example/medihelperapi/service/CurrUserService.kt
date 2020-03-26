package com.example.medihelperapi.service

import com.example.medihelperapi.model.Child
import com.example.medihelperapi.model.Parent
import com.example.medihelperapi.repository.ChildrenRepo
import com.example.medihelperapi.repository.ParentsRepo
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service

@Service
class CurrUserService(
    private val parentsRepo: ParentsRepo,
    private val childrenRepo: ChildrenRepo
) {

    fun expectParent(): Parent {
        val userId = getCurrUserId()
        val parent = parentsRepo.findByIdOrNull(userId)
        return parent ?: throw Forbidden()
    }

    fun expectChild(): Child {
        val userId = getCurrUserId()
        val child = childrenRepo.findByIdOrNull(userId)
        return child ?: throw Forbidden()
    }

    private fun getCurrUserId(): Long {
        val context = SecurityContextHolder.getContext()
        val user = context.authentication.principal as User
        return user.username.toLong()
    }
}