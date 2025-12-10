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
    fun jsonMapper(): JsonMapper {
        val typeValidatorBuilder: BasicPolymorphicTypeValidator.Builder? =
            BasicPolymorphicTypeValidator.builder()
//                .allowIfSubType(Object::class.java)
                .allowIfSubType("com.ldb.bbzz.security.menu.dto")
        return JsonMapper.builder().addModules(SecurityJacksonModules.getModules(this::class.java.classLoader, typeValidatorBuilder)).build()
    }

    @Bean
    fun redisCacheConfiguration(): RedisCacheConfiguration {
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