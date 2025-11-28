package com.ldb.bbzz.security.auth.service

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
    private val jwtUtil: JWTUtils
) {

    fun login(authRequestDto : AuthRequestDto) : AuthResponseDto {
        val authToken = UsernamePasswordAuthenticationToken(authRequestDto.userId, authRequestDto.userPassword)
        authenticationManager.authenticate(authToken)

        val userDetails = customUserDetailsService.loadUserByUsername(authRequestDto.userId)
        val token = jwtUtil.generateToken(userDetails.username)
        return AuthResponseDto(token)
    }
}