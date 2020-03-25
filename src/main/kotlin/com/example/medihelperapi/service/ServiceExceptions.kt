package com.example.medihelperapi.service

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason =  "User not found in database")
class UserNotFoundException : RuntimeException()

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "User has no access to this method")
class ForbiddenException : RuntimeException()

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Incorrect user credentials")
class IncorrectCredentialsException : RuntimeException()

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Parent with given email already exists in database")
class ParentExistsException : RuntimeException()

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Medicine not found in database")
class MedicineNotFoundException : RuntimeException()

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Child not found in database")
class ChildNotFoundException : RuntimeException()
