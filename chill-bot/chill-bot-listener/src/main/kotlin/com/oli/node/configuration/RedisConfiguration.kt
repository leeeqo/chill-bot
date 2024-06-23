package com.oli.node.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate


@Configuration
//@EnableTransactionManagement
class RedisConfiguration {

    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<*, *> {
        val template: RedisTemplate<*, *> = RedisTemplate<Any, Any>()
        template.connectionFactory = connectionFactory
        return template
    }
}