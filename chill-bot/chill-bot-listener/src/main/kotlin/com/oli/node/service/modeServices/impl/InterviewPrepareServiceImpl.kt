package com.oli.node.service.modeServices.impl

//import com.oli.node.state.modes.InterviewSession
import com.oli.node.dao.QuestionDAO
import com.oli.node.dao.SubtopicDAO
import com.oli.node.dao.TopicDAO
import com.oli.node.service.ProducerService
import com.oli.node.service.modeServices.InterviewPrepareService
import com.oli.node.state.InterviewSession
import com.oli.node.state.Session
import com.oli.node.utils.defineModeByText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update

private val kLogger = KotlinLogging.logger {}

@Service
class InterviewPrepareServiceImpl(
    private val redisTemplate: RedisTemplate<String, String>,
    private val producerService: ProducerService,
    private val topicDAO: TopicDAO,
    private val subtopicDAO: SubtopicDAO,
    private val questionDAO: QuestionDAO
) : InterviewPrepareService {
    override fun processInterviewPrepareMessage(update: Update) {//, session: Session) {

        kLogger.info { "I've got message from dispatcher, but I have no instructions to deal with it yet.." }

        // TO_DO


        /*val interviewSession = getCurrentSession(update).sessionMode as InterviewSession

        val replyKeyboard: ReplyKeyboard
        val text: String

        if (interviewSession.topic == null) {
            val topics = topicDAO.findAll()

            replyKeyboard = generateReplyKeyboardWithOptions(topics.stream().map {
                it?.name ?: "Ooops, topic has no name("
            }.toList())

            text = "Choose topic"
        } else if (interviewSession.subtopic == null) {
            val subtopics = subtopicDAO.findAll()

            replyKeyboard = generateReplyKeyboardWithOptions(subtopics.stream().map {
                it?.name ?: "Ooops, topic has no name("
            }.toList())

            text = "Choose subtopic"
        } else {
            replyKeyboard = generateReplyKeyboardWithOptions(listOf("We haven't added questions yet("))

            text = "We are sorry!"
            /*val questions = questionDAO.findAll()

            generateReplyKeyboardWithOptions(questions.stream().map {
                it?.text ?: "Ooops, topic has no name("
            }.toList())

            text = "Answer question"*/
        }*/

        //val replyKeyboardMarkup = generateInlineKeyboardWithOptions(listOf("TO_DO"))//topics.stream().map { //generateReplyKeyboardWithOptions(topics.stream().map {
            //it?.name ?: "Ooops, topic has no name("
        //}.toList())//ReplyKeyboardMarkup(rows)


        /*val sendMessage = SendMessage.builder()
            .chatId(update.message.chatId)
            .replyMarkup(replyKeyboard)
            .text(text)
            .build()

        producerService.producerAnswer(sendMessage)*/
    }

    override fun getCurrentSession(update: Update): Session {
        val chatId = update.message!!.chatId.toString()

        val serializedSession = redisTemplate.opsForValue().get(chatId) ?:
            Json.encodeToString(createNewSession(update))

        kLogger.info { "JSON<Session>.toString() = $serializedSession" }

        return Json.decodeFromString<Session>(serializedSession)
        //return redisTemplate.opsForValue().get(chatId) ?: createNewSession(update)
    }

    override fun createNewSession(update: Update): Session {
        val message = update.message!!
        val chatId = update.message!!.chatId.toString()
        val mode = defineModeByText(message.text)
        val session = Session(chatId, mode, 0, InterviewSession())

        val serializedSession = Json.encodeToString(session)

        redisTemplate.opsForValue().set(chatId, serializedSession)

        return session
    }
}