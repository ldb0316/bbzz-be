package com.ldb.bbzz.security.auth.service

import com.ldb.bbzz.common.caching.service.RedisService
import com.ldb.bbzz.security.auth.dto.AuthRequestDto
import com.ldb.bbzz.security.config.service.CustomUserDetailsService
import com.ldb.bbzz.security.util.JWTUtils
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val customUserDetailsService: CustomUserDetailsService,
    private val jwtUtil: JWTUtils,
    private val redisService: RedisService
) {

    fun login(authRequestDto : AuthRequestDto) : Authentication {
        val authToken = UsernamePasswordAuthenticationToken(authRequestDto.userId, authRequestDto.userPassword)
//        try {
            val authentication: Authentication = authenticationManager.authenticate(authToken)


//        } catch (e : AuthenticationException) {
//            when (e){
//                is UsernameNotFoundException -> {println("아이디틀림")} //사용자ID 못찾았을 때
//                is BadCredentialsException -> {println("비밀번호틀림")} //사용자 비밀번호 불일치
//                is DisabledException -> {} // 계정 비활성 상태
//                is LockedException -> {} // 계정 잠김
//                is AccountExpiredException -> {} // 계정 만료
//                is CredentialsExpiredException -> {} // 비밀번호 만료
//                else -> {}
//            }
//        }


//        val userDetails = customUserDetailsService.loadUserByUsername(authRequestDto.userId)

//        redisService.saveUserRoles(userDetails.username, userDetails.authorities)

//        return userDetails.username
        return authentication
    }


    fun generateAccessToken(authentication: Authentication): String {
        return jwtUtil.generateAccessToken(authentication)
    }

    fun generateRefreshToken(authentication: Authentication): String {
        return jwtUtil.generateRefreshToken(authentication)
    }
}