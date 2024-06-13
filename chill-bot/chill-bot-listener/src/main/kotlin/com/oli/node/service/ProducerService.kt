package com.oli.node.service

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup


interface ProducerService {
    fun producerAnswer(sendMessage: SendMessage)

    //fun produceAnswerWithKeyboard(keyboardMarkup: ReplyKeyboardMarkup)
}