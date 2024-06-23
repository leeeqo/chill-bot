package com.oli.node.service.impl

import com.oli.node.service.ConsumerService
import com.oli.node.service.MainService
import com.oli.node.state.Session
import mu.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update

private val kLogger = KotlinLogging.logger {}

@Service
class ConsumerServiceImpl(
    private val mainService: MainService,
    private val redisTemplate: RedisTemplate<String, Session>
) : ConsumerService {

    @RabbitListener(queues = ["\${spring.rabbitmq.queues.topic-list-request}"])
    override fun consumeTopicListRequests(update: Update) {
        kLogger.debug("NODE: received message")



        mainService.processTextMessage(update)//, session)
    }

    /*@RabbitListener(queues = ["\${spring.rabbitmq.queues.doc-message-update}"])
    override fun consumeDocMessageUpdates(update: Update) {
        kLogger.debug("NODE: Doc message is received")
    }

    @RabbitListener(queues = ["\${spring.rabbitmq.queues.photo-message-update}"])
    override fun consumePhotoMessageUpdates(update: Update) {
        kLogger.debug("NODE: Photo message is received")
    }*/

    /*private fun createNewSession(chatId: String, message: Message): Session {
        val mode = defineModeByText(message.text)
        val session = Session(chatId, mode, 0, InterviewSession())

        redisTemplate.opsForValue().set(chatId, session)

        return session
    }*/
}