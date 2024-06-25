package com.oli.chill_bot.service

import org.telegram.telegrambots.meta.api.methods.send.SendMessage


interface AnswerConsumer {
    fun consume(sendMessage: SendMessage)
}
