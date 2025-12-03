package com.ldb.bbzz.security.util

import io.jsonwebtoken.Jwts
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
    fun generateAccessToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 5)) // 5분
            .signWith(privateKey)
            .compact()
    }

    /**
     * JWT Refresh Token 생성
     */
    fun generateRefreshToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24시간
            .signWith(privateKey)
            .compact()
    }

    fun getUserIdFromToken(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(publicKey)
            .build()
            .parseClaimsJws(token)
            .body.subject
    }
}