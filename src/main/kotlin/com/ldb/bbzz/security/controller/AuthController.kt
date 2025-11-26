package com.ldb.bbzz.security.controller

import com.ldb.bbzz.security.dto.AuthRequestDto
import com.ldb.bbzz.security.dto.AuthResponseDto
import com.ldb.bbzz.security.dto.SignRequestDto
import com.ldb.bbzz.security.service.CustomUserDetailsService
import com.ldb.bbzz.security.service.SignService
import com.ldb.bbzz.security.util.JWTUtils
import jakarta.validation.Valid
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val customUserDetailsService: CustomUserDetailsService,
    private val signService: SignService,
    private val jwtUtil: JWTUtils
) {

    @GetMapping("/me")
    fun me(): Map<String, Any> {
        val auth = SecurityContextHolder.getContext().authentication
        val principal = auth?.principal as? UserDetails?: return mapOf("error" to "User not authenticated")
        return mapOf(
            "username" to principal.username,
            "userId" to principal.authorities.joinToString(","),
        )
    }

    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid signRequestDto: SignRequestDto): String {
        signService.create(signRequestDto)
        return "등록됨"
    }

    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequestDto ): AuthResponseDto {
        val authToken = UsernamePasswordAuthenticationToken(request.userId, request.userPassword)
        authenticationManager.authenticate(authToken)

        val userDetails = customUserDetailsService.loadUserByUsername(request.userId)
        val token = jwtUtil.generateToken(userDetails.username)
        return AuthResponseDto(token)
    }
}