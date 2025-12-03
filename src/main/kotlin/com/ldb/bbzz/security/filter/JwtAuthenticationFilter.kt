package com.ldb.bbzz.security.filter

import com.ldb.bbzz.security.config.service.CustomUserDetailsService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val customUserDetailsService: CustomUserDetailsService,
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        TODO("Custom Filter 구현해서 인증/인가 구현")
    }


}