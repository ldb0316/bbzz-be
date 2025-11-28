package com.ldb.bbzz.security.dto

data class SignRequestDto(
    val userId: String,
    val userName: String,
    val userPassword: String,
    val userRoles: List<UserRoleRequestDto>
)
