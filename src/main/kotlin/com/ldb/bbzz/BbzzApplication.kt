package com.ldb.bbzz

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class BbzzApplication

fun main(args: Array<String>) {
	runApplication<BbzzApplication>(*args)
}
