package com.ldb.bbzz.security.menu.service

import com.ldb.bbzz.security.menu.dto.MenuRoleRspnsDto
import com.ldb.bbzz.security.menu.repository.MenuRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class MenuService(
    private val menuRepository: MenuRepository
) {
    /**
     * 메뉴 URL + HttpMethod 에서 요구하는 권한 목록을 불러온다(자동 캐싱 처리됨)
     */
    @Cacheable("menuRoles", key="#menuUrl+':'+#menuMethod")
    fun getMenuRoles(menuUrl: String, menuMethod: String): List<MenuRoleRspnsDto> {
        val menu = menuRepository.findByMenuUrlAndMenuMethod(menuUrl, menuMethod).orElse(null)
        return menu?.menuRoles?.map{MenuRoleRspnsDto(it.menuRole)} ?: ArrayList()
    }
}