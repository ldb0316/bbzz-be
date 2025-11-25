package com.ldb.bbzz.sample

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Base64

@RestController
@RequestMapping(path = ["/samples"])
class SampleController {

    @GetMapping
    fun getAll():String  {
//        val privateKeyStr = System.getenv("PRIVATE_KEY")
//        val publicKeyStr = System.getenv("PUBLIC_KEY")
//
//        val keyFactory = KeyFactory.getInstance("RSA")
//
//        val privateKeyBytes = Base64.getDecoder().decode(privateKeyStr)
//        val privateKeySpec = PKCS8EncodedKeySpec(privateKeyBytes)
//        val privateKey = keyFactory.generatePrivate(privateKeySpec)
//
//        val publicKeyBytes = Base64.getDecoder().decode(publicKeyStr)
//        val publicKeySpec = X509EncodedKeySpec(publicKeyBytes)
//        val publicKey = keyFactory.generatePublic(publicKeySpec)

        return "hi";
    }

    @GetMapping("/{tsid}")
    fun getOne(@PathVariable tsid:String ):String  {

        return tsid;
    }
}