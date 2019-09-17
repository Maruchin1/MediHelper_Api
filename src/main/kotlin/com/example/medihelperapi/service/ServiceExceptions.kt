package com.example.medihelperapi.service

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason =  "User not found in database")
class UserNotFoundException : RuntimeException()

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Incorrect user credentials")
class IncorrectCredentialsException : RuntimeException()

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Password confirmation don't match password")
class PasswordConfirmationIncorrectException : RuntimeException()