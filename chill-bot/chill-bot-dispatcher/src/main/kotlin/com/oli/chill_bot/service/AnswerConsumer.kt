package com.oli.chill_bot.service

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup


interface AnswerConsumer {
    fun consume(sendMessage: SendMessage)
}
