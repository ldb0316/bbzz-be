package com.ldb.bbzz.security.sign.controller

import com.ldb.bbzz.security.sign.dto.SignRequestDto
import com.ldb.bbzz.security.sign.service.SignService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sign")
class SignController(
    private val signService: SignService
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid signRequestDto: SignRequestDto): String {
        signService.signup(signRequestDto)
        return "등록됨"
    }

}