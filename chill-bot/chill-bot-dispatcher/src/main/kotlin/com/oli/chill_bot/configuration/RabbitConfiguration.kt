package com.oli.chill_bot.configuration

import org.springframework.amqp.core.Queue
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*


@Configuration
class RabbitConfiguration (
    @Value("\${spring.rabbitmq.queues.update-request}")
    val updateRequestQueueVal: String,
    @Value("\${spring.rabbitmq.queues.answer-message}")
    val answerMessageQueueVal: String
) {
    @Bean
    fun updateRequestQueue(): Queue = Queue(updateRequestQueueVal)

    @Bean
    fun answerMessageQueue(): Queue = Queue(answerMessageQueueVal)

    @Bean
    fun jsonMessageConverter(): MessageConverter = Jackson2JsonMessageConverter()
}