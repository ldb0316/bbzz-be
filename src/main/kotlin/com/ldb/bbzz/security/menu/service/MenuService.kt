package com.ldb.bbzz.security.menu.service

import com.ldb.bbzz.security.menu.entity.MenuRole
import com.ldb.bbzz.security.menu.repository.MenuRepository
import org.springframework.stereotype.Service

@Service
class MenuService(
    private val menuRepository: MenuRepository
) {

    fun getMenuRoles(menuUrl: String, menuMethod: String): List<MenuRole> {
        val menu = menuRepository.findByMenuUrlAndMenuMethod(menuUrl, menuMethod).orElse(null)
        return menu?.menuRoles ?: ArrayList()
    }
}