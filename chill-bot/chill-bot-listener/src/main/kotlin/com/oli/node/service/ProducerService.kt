package com.oli.node.service

import org.telegram.telegrambots.meta.api.methods.send.SendMessage


interface ProducerService {

    fun producerAnswer(sendMessage: SendMessage)
}