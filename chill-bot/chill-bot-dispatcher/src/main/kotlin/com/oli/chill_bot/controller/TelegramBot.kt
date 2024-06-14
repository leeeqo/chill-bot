package com.oli.chill_bot.controller

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

private val kLogger = KotlinLogging.logger {}

@Controller
class TelegramBot(
    @Value("\${bot.token}")
    private val token: String,
    private val updateController: UpdateController
): SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
    private val telegramClient = OkHttpTelegramClient(token)

    init {
        updateController.registerBot(this)
    }

    override fun consume(update: Update?) {
        if (update == null) {
            kLogger.error { "Received update is null" }
            return
        }

        updateController.processUpdate(update)
    }

    fun sendAnswerMessage(sendMessage: SendMessage) {
        try {
            telegramClient.execute(sendMessage)
        } catch (e: TelegramApiException) {
            println(e.message)
        }
    }

    override fun getBotToken(): String = token

    override fun getUpdatesConsumer(): LongPollingUpdateConsumer = this
}