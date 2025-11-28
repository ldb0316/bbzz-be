package com.ldb.bbzz.security

import com.ldb.bbzz.security.entity.UserInfo
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(private val userInfo: UserInfo) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> = userInfo.userRoles.map{ CustomGrantedAuthority(it.userRole) }

    override fun getPassword(): String = requireNotNull(userInfo.userPassword) {"사용자 정보 오류"}

    override fun getUsername(): String = requireNotNull(userInfo.userId) {"사용자 정보 오류"}

    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true

}