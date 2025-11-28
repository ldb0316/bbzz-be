package com.ldb.bbzz.security.config

import org.springframework.security.core.GrantedAuthority

class CustomGrantedAuthority(private val role: String): GrantedAuthority {
    override fun getAuthority(): String = role
}