package com.ldb.bbzz.security.auth.controller
import com.ldb.bbzz.security.auth.dto.AuthRequestDto
import com.ldb.bbzz.security.auth.dto.AuthResponseDto
import com.ldb.bbzz.security.auth.service.AuthService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

//    @GetMapping("/redis")
//    fun redis(): ResponseEntity<List<UserRoleCacheDto>> {
//        val roles = redisService.getUserRoles("ldb9060")
//        return ResponseEntity.ok(roles)
//    }

    @PostMapping("/login")
    fun login(@RequestBody authRequestDto: AuthRequestDto, response: HttpServletResponse): ResponseEntity<AuthResponseDto> {
        val authentication: Authentication = authService.login(authRequestDto)
        val accessToken: String = authService.generateAccessToken(authentication)
        val refreshToken: String = authService.generateRefreshToken(authentication)
        val cookie = Cookie("refreshToken", refreshToken)
        cookie.path = "/"
        cookie.isHttpOnly = true
        cookie.secure = true
        cookie.maxAge = 7*24*60*60
        response.addCookie(cookie)

        return ResponseEntity.ok(AuthResponseDto(accessToken))

    }
}