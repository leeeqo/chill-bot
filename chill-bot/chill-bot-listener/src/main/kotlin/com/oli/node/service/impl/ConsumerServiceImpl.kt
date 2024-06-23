package com.oli.node.service.impl

import com.oli.node.dao.SessionRepository
import com.oli.node.service.ConsumerService
import com.oli.node.service.modeServices.InterviewPrepareService
import com.oli.node.state.Mode.PREPARE_FOR_INTERVIEW
import com.oli.node.utils.defineModeByText
import mu.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update

private val kLogger = KotlinLogging.logger {}

@Service
class ConsumerServiceImpl(
    private val sessionRepository: SessionRepository,
    private val interviewPrepareService: InterviewPrepareService
) : ConsumerService {

    @RabbitListener(queues = ["\${spring.rabbitmq.queues.update-request}"])
    override fun consumeRequests(update: Update) {
        kLogger.debug("NODE: received message")

        val chatId = update.message.chatId.toString()
        val currSession = sessionRepository.findById(chatId)

        val mode = if (currSession.isPresent) {
            currSession.get().mode
        } else {
            defineModeByText(update.message.text)
        }

        if (update.message.text == "/end" && currSession.isPresent) {
            sessionRepository.delete(currSession.get())
            return
        }

        when (mode) {
            PREPARE_FOR_INTERVIEW -> interviewPrepareService.processInterviewPrepareMessage(update)//, session)

            //MORE_MORE_SLEEP -> TO_DO

            else -> {
                kLogger.info { "In ELSE branch in MainService" }
            }
        }
    }
}