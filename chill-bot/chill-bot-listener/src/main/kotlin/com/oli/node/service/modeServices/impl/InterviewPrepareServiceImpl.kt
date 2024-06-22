package com.oli.node.service.modeServices.impl

//import com.oli.node.state.modes.InterviewSession
//import kotlinx.serialization.json.Json
import com.oli.node.dao.QuestionDAO
import com.oli.node.dao.SessionRepository
import com.oli.node.dao.SubtopicDAO
import com.oli.node.dao.TopicDAO
import com.oli.node.entity.Subtopic
import com.oli.node.entity.Topic
import com.oli.node.service.ProducerService
import com.oli.node.service.modeServices.InterviewPrepareService
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
import kotlin.random.Random

private val kLogger = KotlinLogging.logger {}

@Service
class InterviewPrepareServiceImpl(
    //private val redisTemplate: RedisTemplate<String, String>,
    private val producerService: ProducerService,
    private val topicDAO: TopicDAO,
    private val subtopicDAO: SubtopicDAO,
    private val questionDAO: QuestionDAO,
    private val sessionRepository: SessionRepository
) : InterviewPrepareService {

    //@Transactional
    override fun processInterviewPrepareMessage(update: Update) {//, session: Session) {

        /*kLogger.info { "I've got message from dispatcher, but I have no instructions to deal with it yet.." }

        val ses = Session("chatId", Mode.MORE_MORE_SLEEP, 0,
            InterviewSession(Topic(10L, "topic_name"),
                Subtopic(11L, "subtopic_name", Topic(10L, "topic_name"))))
        val sesToStr = Json.encodeToString(ses)
        val sesAgain = Json.decodeFromString<Session>(sesToStr)
        kLogger.info {
            "Session = $ses \n" +
            "Session to JSON STRING = $sesToStr \n" +
            "JSON STRING to Session = $sesAgain"
        }

        sessionRepository.save(ses)

        val retrievedSession = sessionRepository.findById("chatId")

        kLogger.info { "RETRIEVED SESSION = ${retrievedSession.get()} \n" + "Its InterviewSession = ${retrievedSession.get().sessionMode}" }*/

        // TO_DO

        kLogger.debug {
            "In processInterviewPrepareMessage"
        }

        val currSession = getCurrentSession(update)
        val interviewSession = currSession.sessionMode as InterviewSession

        kLogger.info {
            "SESSION: \n" +
            "Session topic"
        }

        val replyKeyboard: ReplyKeyboard
        val text: String

        var topicToSession: Topic
        var subtopicToSession: Subtopic

        if (currSession.stage == 0) {//interviewSession.topic == null) {
            val topics = topicDAO.findAll()
            //val topicNames = topics.stream().map { it.name }.toList()

            replyKeyboard = generateReplyKeyboardWithOptions(topics.stream().map {
                it?.name ?: "Ooops, topic has no name("
            }.toList())

            //replyKeyboard = processTopic()

            text = "Choose topic"

            /*println("INFO ABOUT SESSION:")
            println("SessionId = ${currSession.id}")
            println("SessionStage = ${currSession.stage}")
            println("SessionMode = ${currSession.mode}")
            println("SessionInterview = ${currSession.sessionMode}")

            println("INFO ABOUT INTERVIEW_SESSION:")
            println("SessionId = ${interviewSession.topic}")*/
        } else if (currSession.stage == 1) {//(interviewSession.subtopic == null) {

            println("IN IF == 1 BLOCK")
            val topicName = update.message.text

            println("TOPIC = $topicName")

            // TO_DO - write code responsible for distinct topic/subtopic names
            val topic = topicDAO.findAllByName(topicName).last()
            //val topic = topicDAO.findAllByName(topicName)//.last()

            //interviewSession.topic = topic
            topicToSession = topic

            /*println("TOPIC_OBJECT = $topic")

            println("INFO ABOUT SESSION:")
            println("SessionId = ${currSession.id}")
            println("SessionStage = ${currSession.stage}")
            println("SessionMode = ${currSession.mode}")
            println("SessionInterview = ${currSession.sessionMode}")*/

            //println("INFO ABOUT INTERVIEW_SESSION:")
            //println("SessionId = ${interviewSession.topic}")

            val subtopics = subtopicDAO.findAllByTopic(topic)

            replyKeyboard = generateReplyKeyboardWithOptions(subtopics.stream().map {
                it?.name ?: "Ooops, topic has no name("
            }.toList())

            text = "Choose subtopic"

            //currSession.stage++
            //sessionRepository.save(currSession)

            //replyKeyboard = generateReplyKeyboardWithOptions(listOf("ku"))
            //text = "ku"
        } else {
            val subtopicName = update.message.text

            val subtopic = subtopicDAO.findAllByName(subtopicName).last()

            //interviewSession.subtopic = subtopic
            subtopicToSession = subtopic

            var numQuestionsBySubtopic = questionDAO.countBySubtopic(subtopic)

            numQuestionsBySubtopic += (2 - currSession.stage)

            if (numQuestionsBySubtopic == 0L) {
                replyKeyboard = generateReplyKeyboardWithOptions(listOf(
                    "Good",
                    "It must be repeated",
                    "Don't know"
                ))

                //text = "kuku"
                text = "All the questions were shown for this subtopic"
            } else {
                var randomQuestionId: Long
                do {
                    randomQuestionId = Random.nextLong(numQuestionsBySubtopic)
                    //currSession.random.nextLong(numQuestionsBySubtopic)
                } while (randomQuestionId in interviewSession.getIdOfQuestions())

                val question = questionDAO.findById(randomQuestionId)

                replyKeyboard = generateReplyKeyboardWithOptions(listOf(
                    "Good",
                    "It must be repeated",
                    "Don't know"
                ))

                //text = "kuku"
                text = question.get().text
            }



            /*interviewSession.questions.add(question.get())
            sessionRepository.save(currSession)*/

            //val numQuestionsBySubtopic = questionDAO.count()
            //val randQuestionId = currSession.random.nextInt()
            //replyKeyboard = generateReplyKeyboardWithOptions(listOf("We haven't added questions yet("))

            //text = "We are sorry!"
            /*val questions = questionDAO.findAll()

            generateReplyKeyboardWithOptions(questions.stream().map {
                it?.text ?: "Ooops, topic has no name("
            }.toList())

            text = "Answer question"*/
        }

        //val replyKeyboardMarkup = generateInlineKeyboardWithOptions(listOf("TO_DO"))//topics.stream().map { //generateReplyKeyboardWithOptions(topics.stream().map {
            //it?.name ?: "Ooops, topic has no name("
        //}.toList())//ReplyKeyboardMarkup(rows)

        currSession.stage++
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

        //val serializedSession = redisTemplate.opsForValue().get(chatId) ?:
        //    Json.encodeToString(createNewSession(update))

        //val retrievedSession =

        //kLogger.info { "JSON<Session>.toString() = $retrievedSession" }

        return sessionRepository.findById(chatId).orElse(createNewSession(update))
        //return Json.decodeFromString<Session>(serializedSession)
        //return redisTemplate.opsForValue().get(chatId) ?: createNewSession(update)
    }

    override fun createNewSession(update: Update): Session {
        val message = update.message!!
        val chatId = update.message!!.chatId.toString()
        val mode = defineModeByText(message.text)
        val session = Session(chatId, mode, 0, InterviewSession())

        //val serializedSession = Json.encodeToString(session)

        //redisTemplate.opsForValue().set(chatId, serializedSession)

        return sessionRepository.save(session)
        //return session
    }

    @Transactional
    fun processTopic(): ReplyKeyboard {
        val topics = topicDAO.findAll()

        return generateReplyKeyboardWithOptions(topics.stream().map {
            it?.name ?: "Ooops, topic has no name("
        }.toList())
    }

    @Transactional
    fun processSubtopic(topicName: String): ReplyKeyboard {
        // TO_DO - write code responsible for distinct topic/subtopic names
        val topic = topicDAO.findAllByName(topicName).last()

        val subtopics = subtopicDAO.findAllByTopic(topic)

        return generateReplyKeyboardWithOptions(subtopics.stream().map {
            it?.name ?: "Ooops, subtopic has no name("
        }.toList())
    }

    @Transactional
    fun processQuestion(subtopicName: String, stage: Int, questionsIds: List<Long>): String {
        val subtopic = subtopicDAO.findAllByName(subtopicName).last()

        var numQuestionsBySubtopic = questionDAO.countBySubtopic(subtopic)

        numQuestionsBySubtopic += (2 - stage)

        return if (numQuestionsBySubtopic == 0L) {
            "All the questions were shown for this subtopic"
        } else {
            var randomQuestionId: Long
            do {
                randomQuestionId = Random.nextLong(numQuestionsBySubtopic)
                //currSession.random.nextLong(numQuestionsBySubtopic)
            } while (randomQuestionId in questionsIds)

            val question = questionDAO.findById(randomQuestionId)

            question.get().text
        }
    }
}