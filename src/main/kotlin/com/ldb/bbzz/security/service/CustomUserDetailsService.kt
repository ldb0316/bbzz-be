package com.ldb.bbzz.security.service

import com.ldb.bbzz.security.CustomUserDetails
import com.ldb.bbzz.security.repository.UserInfoRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val userInfoRepository: UserInfoRepository) : UserDetailsService {

    override fun loadUserByUsername(userId: String): UserDetails {
        val userInfo = userInfoRepository.findByUserId(userId)?: throw UsernameNotFoundException("로그인 정보가 잘못되었습니다 : $userId")
        return CustomUserDetails(userInfo)
    }
}