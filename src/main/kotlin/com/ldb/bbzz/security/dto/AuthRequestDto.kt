package com.ldb.bbzz.security.dto

import org.springframework.security.crypto.password.PasswordEncoder

data class AuthRequestDto(
    val userId: String,
    val userPassword: String
)
