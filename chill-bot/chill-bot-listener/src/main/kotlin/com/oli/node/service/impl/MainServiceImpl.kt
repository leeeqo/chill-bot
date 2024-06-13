package com.oli.node.service.impl

import com.oli.node.dao.TopicDAO
import com.oli.node.entity.Topic
import com.oli.node.service.MainService
import com.oli.node.service.ProducerService
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

@Service
class MainServiceImpl(
    private val topicDAO: TopicDAO,
    private val producerService: ProducerService
) : MainService {

    override fun processTextMessage(update: Update) {
        val topics = getAllTopics()

        println("NUM OF TOPICS = ${topics.size}")

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
        val replyKeyboard: ReplyKeyboardMarkup = ReplyKeyboardMarkup(listOf(kr))*/

        val rows = mutableListOf<KeyboardRow>()
        for (topic in topics) {
            //val rowInline1: MutableList<KeyboardRow> = mutableListOf()
            val buttonsInRow = KeyboardRow()//mutableListOf<KeyboardButton>()
            val button: KeyboardButton = KeyboardButton(topic?.name ?: "EMPTY")
            buttonsInRow.add(button)
            rows.add(buttonsInRow)
        }

        val replyKeyboardMarkup = ReplyKeyboardMarkup(rows)


        val sendMessage = SendMessage.builder()
            .chatId(update.message.chatId)
            .replyMarkup(replyKeyboardMarkup)
            .text("Num rows in topic = ${topics.size}")
            .build()

        producerService.producerAnswer(sendMessage)

        //producerService.produceAnswerWithKeyboard(replyKeyboard)
    }

    /*private fun saveRawData(update: Update) {
        val rawData = RawData(event = update)

        rawDataDAO.save(rawData)
    }*/

    private fun getAllTopics(): List<Topic> = topicDAO.findAll()
}