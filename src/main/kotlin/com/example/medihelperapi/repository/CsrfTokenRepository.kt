package com.example.medihelperapi.repository

import com.example.medihelperapi.model.CsrfToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CsrfTokenRepository : JpaRepository<CsrfToken, String> {
    
}