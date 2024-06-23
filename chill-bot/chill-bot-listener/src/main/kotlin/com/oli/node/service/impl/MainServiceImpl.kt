package com.oli.node.service.impl

import com.oli.node.dao.SessionRepository
import com.oli.node.dao.TopicDAO
import com.oli.node.service.MainService
import com.oli.node.service.ProducerService
import com.oli.node.service.modeServices.InterviewPrepareService
import com.oli.node.state.Mode.PREPARE_FOR_INTERVIEW
import com.oli.node.utils.defineModeByText
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update

private val kLogger = KotlinLogging.logger {}

@Service
class MainServiceImpl(
    private val topicDAO: TopicDAO,
    private val producerService: ProducerService,
    private val interviewPrepareService: InterviewPrepareService,
    private val sessionRepository: SessionRepository
) : MainService {

    override fun processTextMessage(update: Update) {
        val chatId = update.message.chatId.toString()
        val currSession = sessionRepository.findById(chatId)

        val mode = if (currSession.isPresent) {
            currSession.get().mode
        } else {
            defineModeByText(update.message!!.text!!)
        }

        if (update.message.text == "/end" && currSession.isPresent) {
            sessionRepository.delete(currSession.get())
            return
        }

        when (mode) {
            PREPARE_FOR_INTERVIEW -> interviewPrepareService.processInterviewPrepareMessage(update)//, session)

            //MORE_MORE_SLEEP -> TO_DO

            //UNDEFINED -> TO_DO
            else -> {
                kLogger.info { "In ELSE branch in MainService" }
            }
        }
    }
}