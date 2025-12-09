package com.ldb.bbzz.security.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Base64
import java.util.Date

@Component
class JWTUtils {

    val publicKey: PublicKey = KeyFactory.getInstance("RSA").generatePublic(X509EncodedKeySpec(Base64.getDecoder().decode(System.getenv("PUBLIC_KEY"))))
    val privateKey: PrivateKey = KeyFactory.getInstance("RSA").generatePrivate(PKCS8EncodedKeySpec(Base64.getDecoder().decode(System.getenv("PRIVATE_KEY"))))

    /**
     * JWT Access Token 생성
     */
    fun generateAccessToken(authentication: Authentication): String {
        //TODO claims에 userRoles 추가
        return Jwts.builder()
            .setSubject(authentication.name)
            .addClaims(mapOf("roles" to authentication.authorities.map { it.authority }))
            .setIssuer("bbzz")
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 5)) // 5분
            .signWith(privateKey)
            .compact()
    }

    /**
     * JWT Refresh Token 생성
     */
    fun generateRefreshToken(authentication: Authentication): String {
        return Jwts.builder()
            .setSubject(authentication.name)
            .addClaims(mapOf("roles" to authentication.authorities.map { it.authority }))
            .setIssuer("bbzz")
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24시간
            .signWith(privateKey)
            .compact()
    }

    fun getTokenFromHeader(request: HttpServletRequest) : String? {
        val bearerToken = request.getHeader("Authorization")
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }
        return null
    }

    fun getClaimsFromToken(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(publicKey)
            .requireIssuer("bbzz")
            .build()
            .parseClaimsJws(token).body
    }
}