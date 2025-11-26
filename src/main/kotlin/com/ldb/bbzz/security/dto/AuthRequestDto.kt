package com.ldb.bbzz.security.dto

import com.ldb.bbzz.security.entity.UserInfo
import org.springframework.security.crypto.password.PasswordEncoder

data class AuthRequestDto(
    val userId: String,
    val userPassword: String
)
