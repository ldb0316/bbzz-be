package com.ldb.bbzz.security.sign.service

import com.ldb.bbzz.security.auth.repository.UserInfoRepository
import com.ldb.bbzz.security.entity.UserInfo
import com.ldb.bbzz.security.entity.UserRole
import com.ldb.bbzz.security.sign.dto.SignRequestDto
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class SignService(private val userInfoRepository: UserInfoRepository, private val passwordEncoder: PasswordEncoder) {

    /**
     * 회원가입
     * @param com.ldb.bbzz.security.sign.dto.SignRequestDto
     */
    fun signup(signRequestDto : SignRequestDto) {
        val userInfo : UserInfo = UserInfo.builder().userId(signRequestDto.userId).userName(signRequestDto.userName).userPassword(passwordEncoder.encode(signRequestDto.userPassword)).build()

        signRequestDto.userRoles.forEach { r ->
            val role = UserRole(userTsid = userInfo.userTsid, userRole = r.userRole, userInfo = userInfo)
            userInfo.userRoles.add(role)
        }

        userInfoRepository.save(userInfo)
    }
}