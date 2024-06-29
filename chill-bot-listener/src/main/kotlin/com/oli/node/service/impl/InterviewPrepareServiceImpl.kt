package com.oli.node.service.impl

import com.oli.node.dao.QuestionDAO
import com.oli.node.dao.SessionRepository
import com.oli.node.dao.SubtopicDAO
import com.oli.node.dao.TopicDAO
import com.oli.node.entity.Question
import com.oli.node.misc.Answer
import com.oli.node.service.InterviewPrepareService
import com.oli.node.service.ProducerService
import com.oli.node.state.InterviewSession
import com.oli.node.state.Session
import com.oli.node.utils.defineModeByText
import com.oli.node.utils.generateReplyKeyboardWithOptions
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import java.time.LocalDateTime

private val kLogger = KotlinLogging.logger {}

@Service
class InterviewPrepareServiceImpl(
    private val producerService: ProducerService,
    private val topicDAO: TopicDAO,
    private val subtopicDAO: SubtopicDAO,
    private val questionDAO: QuestionDAO,
    private val sessionRepository: SessionRepository
) : InterviewPrepareService {

    companion object {
        private const val CHOOSE_TOPIC = "Choose topic"
        private const val CHOOSE_SUBTOPIC = "Choose subtopic"

        private val ANSWER_OPTIONS: List<String> = Answer.entries.stream().map { it.text }.toList()
    }

    override fun processInterviewPrepareMessage(update: Update) {
        val currSession = getCurrentSession(update)
        currSession.subSession as InterviewSession

        val replyKeyboard: ReplyKeyboard
        val text: String

        processOnChoosingTopicOrSubtopic(currSession, update)

        when (currSession.stage) {
            0 -> {
                currSession.subSession.topicName = null

                replyKeyboard = processTopic()
                text = CHOOSE_TOPIC

                currSession.stage++
            }

            1 -> {
                val topicName = currSession.subSession.topicName ?: update.message.text

                replyKeyboard = processSubtopic(topicName)
                text = CHOOSE_SUBTOPIC

                currSession.stage++
                currSession.subSession.topicName = topicName
            }

            else -> {
                val subtopicName = currSession.subSession.subtopicName ?: update.message.text

                if (update.message.text in ANSWER_OPTIONS) {
                    currSession.subSession.answers.add(Answer.from(update.message.text))
                }
                val alreadyAskedIds = currSession.subSession.alreadyAsked

                val question = processQuestion(subtopicName, alreadyAskedIds)

                if (question != null) {
                    text = question.text
                    replyKeyboard = generateReplyKeyboardWithOptions(ANSWER_OPTIONS)

                    currSession.subSession.alreadyAsked.add(question.id)
                } else {
                    text = "There are no more questions for this subtopic. \n" +
                            "Choose other topic or subtopic"
                    replyKeyboard = generateReplyKeyboardWithOptions(
                        listOf(
                            CHOOSE_TOPIC,
                            CHOOSE_SUBTOPIC
                        )
                    )
                }

                currSession.subSession.subtopicName = subtopicName
            }
        }

        sessionRepository.save(currSession)

        val sendMessage = SendMessage.builder()
            .chatId(update.message.chatId)
            .replyMarkup(replyKeyboard)
            .text(text)
            .build()

        producerService.producerAnswer(sendMessage)
    }

    override fun getCurrentSession(update: Update): Session {
        val chatId = update.message!!.chatId.toString()

        return sessionRepository.findById(chatId).orElse(createNewSession(update))
    }

    override fun createNewSession(update: Update): Session {
        val message = update.message!!
        val chatId = update.message!!.chatId.toString()
        val mode = defineModeByText(message.text)
        val session = Session(chatId, mode, 0, InterviewSession())

        return sessionRepository.save(session)
    }

    private fun processOnChoosingTopicOrSubtopic(currSession: Session, update: Update) {
        when (update.message.text) {
            CHOOSE_TOPIC -> currSession.stage = 0
            CHOOSE_SUBTOPIC -> currSession.stage = 1

            else -> return
        }
    }

    @Transactional
    private fun processTopic(): ReplyKeyboard {
        val topics = topicDAO.findAll()

        return generateReplyKeyboardWithOptions(topics.stream().map {
            it?.name ?: "Ooops, topic has no name("
        }.toList())
    }

    @Transactional
    private fun processSubtopic(topicName: String): ReplyKeyboard {
        // TO_DO - write code responsible for distinct topic/subtopic names
        val topic = topicDAO.findAllByName(topicName).last()

        val subtopics = subtopicDAO.findAllByTopic(topic)

        return generateReplyKeyboardWithOptions(subtopics.stream().map {
            it?.name ?: "Ooops, subtopic has no name("
        }.toList())
    }

    @Transactional
    private fun processQuestion(subtopicName: String, alreadyAskedIds: List<Long>): Question? {
        val questions =
            if (alreadyAskedIds.isEmpty()) {
                questionDAO.findRandBySubtopic(subtopicName)
            } else {
                questionDAO.findRandBySubtopicExceptAlreadyAsked(subtopicName, alreadyAskedIds)
            }

        return if (questions.isEmpty()) null else questions[0]
    }
}