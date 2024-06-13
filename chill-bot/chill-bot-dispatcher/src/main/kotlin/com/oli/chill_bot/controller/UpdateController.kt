package com.oli.chill_bot.controller

import com.oli.chill_bot.configuration.RabbitConfiguration
import com.oli.chill_bot.service.UpdateProducer
import com.oli.chill_bot.utils.generateSendMessageWithText
import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup

private val kLogger = KotlinLogging.logger {}

@Controller
class UpdateController (
    private val updateProducer: UpdateProducer,
    private val rabbitConfiguration: RabbitConfiguration,
) {
    private lateinit var telegramBot: TelegramBot

    fun registerBot(telegramBot: TelegramBot) {
        this.telegramBot = telegramBot
    }

    fun processUpdate(update: Update) {
        if (!update.hasMessage()) {
            kLogger.error { "Received unsupported message type $update" }
        }

        distributeMessagesByType(update)
    }

    private fun distributeMessagesByType(update: Update) {
        update.message.text?.let {
            if (it == "Train") {
                setView(generateSendMessageWithText(update, "Choose Topic"))
            } else if (it == "Java Core") {
                processJavaCoreMessage(update)
            } else {
                generateSendMessageWithText(update, "Unsopported text")
            }
        }

        /*if (message.hasText()) {
            processTextMessage(update)
        } else if (message.hasDocument()) {
            processDocMessage(update)
        } else if (message.hasPhoto()) {
            processPhotoMessage(update)
        } else {
            setUnsupportedMessageTypeView(update)
        }*/

    }

    private fun setUnsupportedMessageAnswer(update: Update) {
        setView(generateSendMessageWithText(update, "Неподдерживаемый тип сообщения!"))
    }

    /*private fun setFileIsReceivedView(update: Update) {
        val sendMessage: Unit = messageUtils.generateSendMessageWithText(
            update,
            "Файл получен! Обрабатывается..."
        )
        setView(sendMessage)
    }*/

    fun setView(sendMessage: SendMessage) {
        telegramBot.sendAnswerMessage(sendMessage)
    }

    fun processJavaCoreMessage(update: Update) {
        updateProducer.produce(rabbitConfiguration.topicListQueueVal, update)
    }

    /*fun processPhotoMessage(update: Update) {
        updateProducer.produce(rabbitConfiguration.getPhotoMessageUpdateQueue(), update)
        setFileIsReceivedView(update)
    }

    private fun processDocMessage(update: Update) {
        updateProducer.produce(rabbitConfiguration.getDocMessageUpdateQueue(), update)
        setFileIsReceivedView(update)
    }

    private fun processTextMessage(update: Update) {
        updateProducer.produce(rabbitConfiguration.getTextMessageUpdateQueue(), update)
    }*/
}