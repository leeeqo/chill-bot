package com.oli.chill_bot.utils

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

fun Update.generateSendMessageWithText(text: String): SendMessage = SendMessage.builder()
    .chatId(message.chatId)
    .text(text)
    .build()

fun Update.generateSendMessageWithOptionsAndHeader(
    header: String,
    options: List<String>,
): SendMessage = SendMessage.builder()
    .chatId(message.chatId)
    .text(header)
    .replyMarkup(generateKeyboardWithOptions(options))
    .build()


fun generateKeyboardWithOptions(options: List<String>): ReplyKeyboardMarkup {
    val rows = mutableListOf<KeyboardRow>()

    for (option in options) {
        val row = KeyboardRow()
        row.add(KeyboardButton(option))

        rows.add(row)
    }

    return ReplyKeyboardMarkup(rows)
}

fun Update.generateSendMessageWithRemoveKeyboard(text: String): SendMessage = SendMessage.builder()
    .chatId(message.chatId)
    .replyMarkup(ReplyKeyboardRemove(true))
    .text(text)
    .build()
