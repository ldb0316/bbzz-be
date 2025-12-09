package com.ldb.bbzz.security.filter

import com.ldb.bbzz.security.menu.entity.MenuRole
import com.ldb.bbzz.security.menu.service.MenuService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class MenuAuthorizationFilter(
    private val menuService: MenuService,
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        //TODO redis 기반 캐싱 구현 필요
        val uri = request.requestURI.lowercase()
        val method = request.method.uppercase()
        val roles: List<MenuRole> = menuService.getMenuRoles(uri, method)
        val requiredRoles = roles.map { it.menuRole }
        val authentication = SecurityContextHolder.getContext().authentication
        val userRoles = authentication?.authorities?.map { it.authority }.orEmpty() // 사용자 권한이 없으면 빈 List 반환
        val accessible = userRoles.intersect(requiredRoles.toSet()).isNotEmpty().or(requiredRoles.isEmpty()) // 사용자 권한과 필요권한 교집합 추출. 만약 교집합이 비어있으면 필요권한이 비어있는지 확인
        if(!accessible){
            throw AccessDeniedException("접근 권한이 없습니다.")
        } else {
            filterChain.doFilter(request, response)
        }
    }

}