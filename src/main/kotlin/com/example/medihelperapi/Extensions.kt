package com.example.medihelperapi

import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.userdetails.User

fun SecurityContext.getAuthenticatedUserEmail() = (this.authentication.principal as User).username