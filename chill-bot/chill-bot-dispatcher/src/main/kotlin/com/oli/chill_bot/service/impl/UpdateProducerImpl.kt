package com.oli.chill_bot.service.impl

import com.oli.chill_bot.service.UpdateProducer
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class UpdateProducerImpl(
    private val rabbitTemplate: RabbitTemplate,
): UpdateProducer {
    override fun produce(queue: String, update: Update) {
        rabbitTemplate.convertAndSend(queue, update)
    }
}