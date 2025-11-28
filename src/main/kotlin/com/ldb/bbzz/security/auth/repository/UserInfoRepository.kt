package com.ldb.bbzz.security.auth.repository

import com.ldb.bbzz.security.entity.UserInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserInfoRepository : JpaRepository<UserInfo, String> {
    fun findByUserId(userId: String): UserInfo?
}