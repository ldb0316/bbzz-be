package com.ldb.bbzz.security.config

import com.ldb.bbzz.security.config.service.CustomUserDetailsService
import com.ldb.bbzz.security.filter.JwtAuthenticationFilter
import com.ldb.bbzz.security.filter.MenuAuthorizationFilter
import com.ldb.bbzz.security.menu.service.MenuService
import com.ldb.bbzz.security.util.JWTUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val customUserDetailsService : CustomUserDetailsService,
    private val menuService: MenuService,
    private val jwtUtils: JWTUtils
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationProvider() : AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider(customUserDetailsService)
//        authProvider.setUserDetailsService(customUserDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    fun jwtAuthenticationFilter() : JwtAuthenticationFilter {
        return JwtAuthenticationFilter(customUserDetailsService, jwtUtils)
    }

    @Bean
    fun menuAuthorizationFilter() : MenuAuthorizationFilter {
        return MenuAuthorizationFilter(menuService)
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/auth/**").permitAll()
                it.requestMatchers("/sign/**").permitAll()
                it.anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterAfter(menuAuthorizationFilter(), JwtAuthenticationFilter::class.java)
        return http.build()
    }
}