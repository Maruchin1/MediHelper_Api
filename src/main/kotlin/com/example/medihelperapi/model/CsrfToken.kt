package com.example.medihelperapi.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class CsrfToken(
    @Id
    val value: String = ""
)