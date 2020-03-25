package com.example.medihelperapi.service

import com.example.medihelperapi.dto.GetChildDto
import com.example.medihelperapi.dto.LoginChildDto
import com.example.medihelperapi.dto.PostChildDto
import com.example.medihelperapi.repository.ChildrenRepo
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChildrenService (
    private val childrenRepo: ChildrenRepo,
    private val currUserService: CurrUserService
) {

    fun login(dto: LoginChildDto): String {
        val child = childrenRepo.findByConnectionKey(dto.connectionKey)
            .orElseThrow { ChildNotFoundException() }
        child.authToken = UUID.randomUUID().toString()
        val savedChild = childrenRepo.save(child)
        return savedChild.authToken
    }

    fun addNew(dto: PostChildDto) {
        val parent = currUserService.expectParent()
        val newChild = dto.toEntity(parent)
        childrenRepo.save(newChild)
    }

    fun getAll(): List<GetChildDto> {
        val parent = currUserService.expectParent()
        return childrenRepo.findAllByParent(parent).map {
            GetChildDto(it)
        }
    }
}