package com.ldb.bbzz.security.service

import com.ldb.bbzz.security.dto.AuthRequestDto
import com.ldb.bbzz.security.dto.AuthResponseDto
import com.ldb.bbzz.security.dto.SignRequestDto
import com.ldb.bbzz.security.entity.UserInfo
import com.ldb.bbzz.security.repository.UserInfoRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class SignService(private val userInfoRepository: UserInfoRepository, private val passwordEncoder: PasswordEncoder) {

    fun create(signRequestDto : SignRequestDto) {
        val userInfo : UserInfo = signRequestDto.toEntity()
        userInfo.setNewPassword(signRequestDto.userPassword, passwordEncoder)
        userInfoRepository.save(userInfo)
    }
}