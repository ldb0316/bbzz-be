package com.ldb.bbzz.security.menu.repository

import com.ldb.bbzz.security.menu.entity.Menu
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface MenuRepository: JpaRepository<Menu, String> {

    fun findByMenuUrlAndMenuMethod(menuUrl: String, menuMethod: String): Optional<Menu>
}