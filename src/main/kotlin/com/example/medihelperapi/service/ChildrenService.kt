package com.example.medihelperapi.service

import com.example.medihelperapi.dto.*
import com.example.medihelperapi.repository.ChildrenRepo
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChildrenService(
    private val childrenRepo: ChildrenRepo,
    private val userService: UserService
) {

    fun addNew(dto: PostChildDto) {
        val parent = userService.expectParent()
        val newChild = dto.toEntity(parent)
        childrenRepo.save(newChild)
    }

    fun getAll(): List<GetChildDto> {
        val parent = userService.expectParent()
        return childrenRepo.findAllByParent(parent).map {
            GetChildDto(it)
        }
    }

    fun delete(id: Long) {
        childrenRepo.deleteById(id)
    }

    fun getChildAndParentPair(): GetChildWithParentDto {
        val child = userService.expectChild()
        return GetChildWithParentDto(child)
    }
}