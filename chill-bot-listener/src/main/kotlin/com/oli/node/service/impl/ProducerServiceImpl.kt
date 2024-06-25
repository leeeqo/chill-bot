package com.oli.node.service.impl

import com.oli.node.service.ProducerService
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage


@Service
class ProducerServiceImpl(
    @Value("\${spring.rabbitmq.queues.answer-message}")
    private val answerMessageQueue: String,
    private val rabbitTemplate: RabbitTemplate,
)  : ProducerService {

    override fun producerAnswer(sendMessage: SendMessage) {
        rabbitTemplate.convertAndSend(answerMessageQueue, sendMessage)
    }
}