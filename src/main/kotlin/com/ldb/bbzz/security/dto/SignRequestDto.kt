package com.ldb.bbzz.security.dto

import com.ldb.bbzz.security.entity.UserInfo

data class SignRequestDto(
    val userId: String,
    val userName: String,
    val userPassword: String
) {
    fun toEntity() : UserInfo {
        val userInfo = UserInfo(
            userId = this.userId,
            userName = this.userName,
            userPassword = "",
        )
        return userInfo
    }
}
