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

    override fun processTextMessage(update: Update) {//, session: Session) {
        //val topics = getAllTopics()

        //val mode = session.
        //val mode = defineModeByText(update.message!!.text!!)

        val chatId = update.message.chatId.toString()

        kLogger.debug {
            "In MainService: \n\n" +
            "CHAT_ID = $chatId"
        }
        /*val mode = sessionRepository.findById(chatId)
            .ifPresentOrElse({session -> session.mode}, {
                defineModeByText(update.message!!.text!!)
            })*/
        val currSession = sessionRepository.findById(chatId)

        kLogger.debug {
            "Is there session in Redis? = ${currSession.isPresent}"
        }

        if (currSession.isPresent) {
            kLogger.debug {
                "Session: $currSession"
            }
        }

        val mode = if (currSession.isPresent) {
            currSession.get().mode
        } else {
            defineModeByText(update.message!!.text!!)
        }
        //val mode = sessionRepository.findById(chatId)

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

        /*val message = update.message
        val sendMessage = SendMessage.builder()
            .chatId(message.chatId)
            .text("Hello from Node")
            .build()*/

        /*val kr: KeyboardRow = KeyboardRow()
        for (topic in topics) {
            val keyBoardButton: KeyboardButton = KeyboardButton(topic.name ?: "STRING IS NULL")
            kr.add(keyBoardButton)
        }
        val replyKeyboard: ReplyKeyboardMarkup = ReplyKeyboardMarkup(listOf(kr))

        val rows = mutableListOf<KeyboardRow>()
        for (topic in topics) {
            //val rowInline1: MutableList<KeyboardRow> = mutableListOf()
            val buttonsInRow = KeyboardRow()//mutableListOf<KeyboardButton>()
            val button: KeyboardButton = KeyboardButton(topic?.name ?: "EMPTY")
            buttonsInRow.add(button)
            rows.add(buttonsInRow)
        }*/

        /*val replyKeyboardMarkup = generateInlineKeyboardWithOptions(topics.stream().map { //generateReplyKeyboardWithOptions(topics.stream().map {
            it?.name ?: "Ooops, topic has no name("
        }.toList())//ReplyKeyboardMarkup(rows)


        val sendMessage = SendMessage.builder()
            .chatId(update.message.chatId)
            .replyMarkup(replyKeyboardMarkup)
            .text("Num rows in topic = ${topics.size}")
            .build()

        producerService.producerAnswer(sendMessage)*/

        //producerService.produceAnswerWithKeyboard(replyKeyboard)
    }

    /*private fun saveRawData(update: Update) {
        val rawData = RawData(event = update)

        rawDataDAO.save(rawData)
    }*/

    //private fun getAllTopics(): List<Topic> = topicDAO.findAll()

    /*fun getCurrentSession(update: Update): Session {
        val chatId = update.message!!.chatId.toString()

        //val serializedSession = redisTemplate.opsForValue().get(chatId) ?:
        //    Json.encodeToString(createNewSession(update))

        //val retrievedSession =

        //kLogger.info { "JSON<Session>.toString() = $retrievedSession" }

        return sessionRepository.findById(chatId).orElse(createNewSession(update))
        //return Json.decodeFromString<Session>(serializedSession)
        //return redisTemplate.opsForValue().get(chatId) ?: createNewSession(update)
    }*/

    /*fun createNewSession(update: Update): Session {
        val message = update.message!!
        val chatId = update.message!!.chatId.toString()
        val mode = defineModeByText(message.text)
        val session = Session(chatId, mode, 0, InterviewSession())

        //val serializedSession = Json.encodeToString(session)

        //redisTemplate.opsForValue().set(chatId, serializedSession)

        return sessionRepository.save(session)
        //return session
    }*/
}