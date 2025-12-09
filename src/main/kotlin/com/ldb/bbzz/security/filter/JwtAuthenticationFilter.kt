package com.ldb.bbzz.security.filter

import com.ldb.bbzz.security.config.CustomGrantedAuthority
import com.ldb.bbzz.security.config.service.CustomUserDetailsService
import com.ldb.bbzz.security.util.JWTUtils
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

/**
 * Jwt 인증 filter
 */
class JwtAuthenticationFilter(
    private val customUserDetailsService: CustomUserDetailsService,
    private val jwtUtils: JWTUtils
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val bearerToken:String? = jwtUtils.getTokenFromHeader(request)
        bearerToken?.let {
            // TODO token expired 확인해서 refesh 로직추가

            val claims = jwtUtils.getClaimsFromToken(it)
//            val customUserDetails = customUserDetailsService.loadUserByUsername(claims.subject)
            val roles = claims["roles"] as? List<*>
            val authorities = roles?.mapNotNull{ item -> item as String}?.map { authority -> CustomGrantedAuthority(authority) }.orEmpty()
            val auth = UsernamePasswordAuthenticationToken(claims.subject, null, authorities)
            SecurityContextHolder.getContext().authentication = auth
        }
        filterChain.doFilter(request, response)
    }


}