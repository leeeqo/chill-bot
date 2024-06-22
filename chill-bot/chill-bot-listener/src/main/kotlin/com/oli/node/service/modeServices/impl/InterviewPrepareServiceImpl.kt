package com.oli.node.service.modeServices.impl

//import com.oli.node.state.modes.InterviewSession
//import kotlinx.serialization.json.Json
import com.oli.node.dao.QuestionDAO
import com.oli.node.dao.SessionRepository
import com.oli.node.dao.SubtopicDAO
import com.oli.node.dao.TopicDAO
import com.oli.node.entity.Question
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

        if (currSession.stage == 0) {//interviewSession.topic == null) {
            replyKeyboard = processTopic()

            text = "Choose topic"
        } else if (currSession.stage == 1) {//(interviewSession.subtopic == null) {
            val topicName = update.message.text

            replyKeyboard = processSubtopic(topicName)

            text = "Choose subtopic"

            currSession.sessionMode.topic = topicName
        } else if (currSession.stage == 2) {
            val subtopicName = update.message.text

            //val questionsIds = currSession.sessionMode.questions.toList()   //currSession.sessionMode.getIdOfQuestions().toList()

            //text = processQuestion(subtopicName, 2, questionsIds)
            val questionOrNull = processQuestionFirstTime(subtopicName)
            text = questionOrNull?.text ?: "There are no questions for this subtopic"

            replyKeyboard = generateReplyKeyboardWithOptions(listOf(
                "Good",
                "It must be repeated",
                "Don't know"
            ))

            currSession.sessionMode.subtopic = subtopicName
            if (questionOrNull != null) {
                currSession.sessionMode.questions.add(questionOrNull.id)
            }

        } else {
            val subtopicName = currSession.sessionMode.subtopic!!

            val questionsIds = currSession.sessionMode.questions.toList() //currSession.sessionMode.getIdOfQuestions().toList()

            val questionOrNull = processQuestion(subtopicName, questionsIds)
            text = questionOrNull?.text ?: "There is no more questions for this subtopic"

            replyKeyboard = generateReplyKeyboardWithOptions(listOf(
                "Good",
                "It must be repeated",
                "Don't know"
            ))

            if (questionOrNull != null) {
                currSession.sessionMode.questions.add(questionOrNull.id)
            }
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
    fun processQuestionFirstTime(subtopicName: String): Question? {
        /*val subtopic = subtopicDAO.findAllByName(subtopicName).last()

        val numQuestionsBySubtopic = questionDAO.countBySubtopic(subtopic)

        return if (numQuestionsBySubtopic == 0L) {
            null
        } else {
            val randomQuestionId = Random.nextLong(numQuestionsBySubtopic)

            val question = questionDAO.findById(randomQuestionId)

            question.get()
        }*/

        val questions = questionDAO.findRandBySubtopic(subtopicName)

        return if (questions.isEmpty()) null else questions[0]
    }

    @Transactional
    fun processQuestion(subtopicName: String, questionIds: List<Long>): Question? {
        /*val subtopic = subtopicDAO.findAllByName(subtopicName).last()

        val questions = questionDAO.findAllBySubtopicExceptAlreadyAsked(subtopicName, questionIds)

        val numLeftQuestions = questions.size

        return if (numLeftQuestions == 0) {
            null
        } else {
            val randomQuestionId = Random.nextInt(numLeftQuestions)

            questions[randomQuestionId]
        }*/

        val questions = questionDAO.findRandBySubtopicExceptAlreadyAsked(subtopicName, questionIds)

        return if (questions.isEmpty()) null else questions[0]
    }
}