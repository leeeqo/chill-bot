package com.oli.chill_bot.utils

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

fun generateSendMessageWithText(update: Update, text: String): SendMessage = SendMessage.builder()
        .chatId(update.message.chatId)
        .text(text)
        .build()