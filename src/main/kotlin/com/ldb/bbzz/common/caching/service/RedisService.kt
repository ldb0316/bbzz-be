package com.ldb.bbzz.common.caching.service

import com.ldb.bbzz.security.auth.dto.UserRoleCacheDto
import com.ldb.bbzz.security.entity.UserInfo
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Service
import tools.jackson.core.type.TypeReference
import tools.jackson.databind.ObjectMapper

@Service
class RedisService(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val objectMapper: ObjectMapper
) {

    fun saveUserRoles(userId: String, userAuthorities: Collection<GrantedAuthority>) {
        val userRoles = userAuthorities.map{ it.authority?.let { userRole -> UserRoleCacheDto(userRole) } }
        redisTemplate.opsForValue().set("userRoles:${userId}", userRoles)
    }

    fun getUserRoles(userId: String): List<UserRoleCacheDto> {
        val value = redisTemplate.opsForValue().get("userRoles:${userId}")
        val json = objectMapper.writeValueAsString(value)
        // TODO null 예외처리 필요
        return objectMapper.readValue(json, object : TypeReference<List<UserRoleCacheDto>>() {})
    }

}