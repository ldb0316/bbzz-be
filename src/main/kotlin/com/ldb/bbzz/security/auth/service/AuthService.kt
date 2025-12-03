package com.ldb.bbzz.security.auth.service

import com.ldb.bbzz.common.caching.service.RedisService
import com.ldb.bbzz.security.auth.dto.AuthRequestDto
import com.ldb.bbzz.security.auth.dto.AuthResponseDto
import com.ldb.bbzz.security.config.service.CustomUserDetailsService
import com.ldb.bbzz.security.util.JWTUtils
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val customUserDetailsService: CustomUserDetailsService,
    private val jwtUtil: JWTUtils,
    private val redisService: RedisService
) {

    fun login(authRequestDto : AuthRequestDto) : String {
        val authToken = UsernamePasswordAuthenticationToken(authRequestDto.userId, authRequestDto.userPassword)
        authenticationManager.authenticate(authToken)

        val userDetails = customUserDetailsService.loadUserByUsername(authRequestDto.userId)

        redisService.saveUserRoles(userDetails.username, userDetails.authorities)

        return userDetails.username
    }

    fun generateAccessToken(userId: String): String {
        return jwtUtil.generateAccessToken(userId)
    }

    fun generateRefreshToken(userId: String): String {
        return jwtUtil.generateRefreshToken(userId)
    }
}