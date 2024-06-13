package com.oli.node.configuration

import org.springframework.amqp.core.Queue
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/*@Configuration
class RabbitConfiguration {
    @Bean
    fun jsonMessageConverter() : MessageConverter = Jackson2JsonMessageConverter()
}*/

@Configuration
class RabbitConfiguration {
    @Bean
    fun jsonMessageConverter(): MessageConverter = Jackson2JsonMessageConverter()
}
