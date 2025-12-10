package com.ldb.bbzz.common.caching

import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.security.jackson.SecurityJacksonModules
import tools.jackson.databind.json.JsonMapper
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator


@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    fun jsonMapper(): JsonMapper { // Jackson3에서는 ObjectMapper대신 JsonMapper를 사용한다.
        //PolymorphicTypeValidator 기반으로 별도 커스텀 타입 설정이 없으면 Spring Security 타입만 기본 허용된다.
        val typeValidatorBuilder: BasicPolymorphicTypeValidator.Builder? =
            BasicPolymorphicTypeValidator.builder()
//                .allowIfSubType(Object::class.java)
                .allowIfSubType("com.ldb.bbzz.security.menu.dto") // 단일 클래스, 패키지 경로, Object 지정을 통한 전체 허용 등 설정 가능
        return JsonMapper.builder().addModules(SecurityJacksonModules.getModules(this::class.java.classLoader, typeValidatorBuilder)).build()
    }

    @Bean
    fun redisCacheConfiguration(): RedisCacheConfiguration {
        //boot4 + Jackson3에서는 GenericJacksonJsonRedisSerializer를 사용한다(Jackson2 X)
        return RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(
                GenericJacksonJsonRedisSerializer(jsonMapper())
            )
        )
    }

    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {

        val template = RedisTemplate<String, Any>()
        template.connectionFactory = connectionFactory
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = GenericJacksonJsonRedisSerializer(jsonMapper())
        return template
    }

}