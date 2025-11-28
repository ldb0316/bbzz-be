package com.ldb.bbzz.security.auth.controller

import com.ldb.bbzz.security.config.service.CustomUserDetailsService
import com.ldb.bbzz.security.auth.dto.AuthRequestDto
import com.ldb.bbzz.security.auth.dto.AuthResponseDto
import com.ldb.bbzz.security.auth.service.AuthService
import com.ldb.bbzz.security.util.JWTUtils
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
    private val authService: AuthService
) {

    @GetMapping("/me")
    fun me(): Map<String, Any> {
        val auth = SecurityContextHolder.getContext().authentication
        val principal = auth?.principal as? UserDetails ?: return mapOf("error" to "User not authenticated")
        return mapOf(
            "username" to principal.username,
            "userId" to principal.authorities.joinToString(","),
        )
    }

    @PostMapping("/login")
    fun login(@RequestBody authRequestDto: AuthRequestDto): AuthResponseDto {
        return authService.login(authRequestDto)
    }
}