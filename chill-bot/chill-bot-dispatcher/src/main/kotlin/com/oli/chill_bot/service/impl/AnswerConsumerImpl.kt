package com.oli.chill_bot.service.impl

import com.oli.chill_bot.controller.UpdateController
import com.oli.chill_bot.service.AnswerConsumer
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup

@Service
class AnswerConsumerImpl(
    private val updateController: UpdateController,
) : AnswerConsumer {

    @RabbitListener(queues = ["\${spring.rabbitmq.queues.answer-message}"])
    override fun consume(sendMessage: SendMessage) {
        updateController.setView(sendMessage)
    }
}