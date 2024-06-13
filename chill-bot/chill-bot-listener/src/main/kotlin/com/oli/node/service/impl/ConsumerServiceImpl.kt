package com.oli.node.service.impl

import com.oli.node.configuration.RabbitConfiguration
import com.oli.node.service.ConsumerService
import com.oli.node.service.MainService
import mu.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update

private val kLogger = KotlinLogging.logger {}

@Service
class ConsumerServiceImpl(
    private val mainService: MainService,
) : ConsumerService {

    @RabbitListener(queues = ["\${spring.rabbitmq.queues.topic-list-request}"])
    override fun consumeTopicListRequests(update: Update) {
        kLogger.debug("NODE: Questions list request received")
        println("SUCCESS")
        mainService.processTextMessage(update)
    }

    /*@RabbitListener(queues = ["\${spring.rabbitmq.queues.doc-message-update}"])
    override fun consumeDocMessageUpdates(update: Update) {
        kLogger.debug("NODE: Doc message is received")
    }

    @RabbitListener(queues = ["\${spring.rabbitmq.queues.photo-message-update}"])
    override fun consumePhotoMessageUpdates(update: Update) {
        kLogger.debug("NODE: Photo message is received")
    }*/
}