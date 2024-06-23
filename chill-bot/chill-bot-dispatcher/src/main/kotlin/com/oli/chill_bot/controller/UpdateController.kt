package com.oli.chill_bot.controller

import com.oli.chill_bot.configuration.RabbitConfiguration
import com.oli.chill_bot.service.UpdateProducer
import com.oli.chill_bot.utils.generateSendMessageWithOptionsAndHeader
import com.oli.chill_bot.utils.generateSendMessageWithRemoveKeyboard
import com.oli.chill_bot.utils.generateSendMessageWithText
import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

private val kLogger = KotlinLogging.logger {}

@Controller
class UpdateController (
    private val updateProducer: UpdateProducer,
    private val rabbitConfiguration: RabbitConfiguration,
) {
    private lateinit var telegramBot: TelegramBot

    companion object {
        private const val UNSUPPORTED_MESSAGE_TYPE = "Unsupported message type"

        private const val START = "/start"
        private const val END = "/end"
        private const val HELP = "/help"

        private const val WHAT_TO_DO = "What would you like to do?"
        private const val PREPARE_FOR_INTERVIEW = "Let's prepare for an interview!"
        private const val MORE_MORE_SLEEP = "Let's go sleep, I'm done!"
    }

    fun registerBot(telegramBot: TelegramBot) {
        this.telegramBot = telegramBot
    }

    fun processUpdate(update: Update) {
        if (!update.hasMessage()) {
            kLogger.error { UNSUPPORTED_MESSAGE_TYPE + update }
        }

        distributeMessagesByType(update)
    }

    private fun distributeMessagesByType(update: Update) = with(update) {
        message.text?.let {
            when (it) {
                START -> setView(
                    generateSendMessageWithOptionsAndHeader(
                        WHAT_TO_DO,
                        listOf(
                            PREPARE_FOR_INTERVIEW,
                            //MORE_MORE_SLEEP
                        )
                    )
                )

                HELP -> setView(
                    generateSendMessageWithText("We haven't written documentation yet(")
                )

                END -> {
                    setView(
                        generateSendMessageWithRemoveKeyboard("Session ended")
                    )
                    toProducer()
                }

                else -> toProducer()
            }
        } ?: run {
            generateSendMessageWithText(UNSUPPORTED_MESSAGE_TYPE)
        }
    }

    fun setView(sendMessage: SendMessage) {
        telegramBot.sendAnswerMessage(sendMessage)
    }

    fun Update.toProducer() {
        updateProducer.produce(rabbitConfiguration.topicListQueueVal, this)
    }
}