package com.oli.chill_bot.service.impl

import com.oli.chill_bot.controller.UpdateController
import com.oli.chill_bot.service.AnswerConsumer
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Service
class AnswerConsumerImpl(
    private val updateController: UpdateController,
    //private val replyTemplate: RedisTemplate<String, List<String>>
) : AnswerConsumer {

    companion object {
        private const val REPLY = "-reply"
        private const val STATISTICS = "-statistics"
    }

    @RabbitListener(queues = ["\${spring.rabbitmq.queues.answer-message}"])
    override fun consume(sendMessage: SendMessage) {
        /*val id: String = sendMessage.chatId

        sendMessage.replyMarkup?.let {
            if (it is ReplyKeyboardMarkup) {
                replyTemplate.opsForValue().set(id + REPLY, it.options())
            } else {
                TO_DO
            }
        }*/

        updateController.setView(sendMessage)
    }

    /*@RabbitListener(queues = ["\${spring.rabbitmq.queues.answer-message-with-keyboard}"])
    override fun consumeKeyboard(sendMessage: SendMessage) {
        val id: String = sendMessage.chatId
        val replyKeyboard = sendMessage.replyMarkup

        replyTemplate.opsForValue().set(id + REPLY, replyKeyboard)
    }*/
}