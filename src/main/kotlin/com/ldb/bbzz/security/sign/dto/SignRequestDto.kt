package com.ldb.bbzz.security.sign.dto

data class SignRequestDto(
    val userId: String,
    val userName: String,
    val userPassword: String,
    val userRoles: List<UserRoleRequestDto>
)
