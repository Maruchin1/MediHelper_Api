package com.example.medihelperapi

import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.userdetails.User

fun SecurityContext.getCurrUser() = this.authentication.principal as User