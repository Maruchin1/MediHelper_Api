package com.example.medihelperapi.controller

import com.example.medihelperapi.config.UserRole
import com.example.medihelperapi.repository.ChildrenRepo
import com.example.medihelperapi.repository.ParentsRepo
import org.springframework.web.bind.annotation.*

@RestController
//@RequestMapping("/user")
class UserController(
        private val parentsRepo: ParentsRepo,
        private val childrenRepo: ChildrenRepo) {

    @GetMapping("/user/role")
    fun getUserRole(@RequestHeader(name = "Authorization") authToken: String): String {

        val parent = parentsRepo.findByAuthToken(authToken)
        if (parent.isPresent) {
            return UserRole.PARENT.toString()
        }
        val child = childrenRepo.findByAuthToken(authToken)
        if (child.isPresent) {
           return UserRole.CHILD.toString()
        }
        return UserRole.GUEST.toString()
    }

}