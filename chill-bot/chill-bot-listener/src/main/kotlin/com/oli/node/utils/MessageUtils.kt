package com.oli.node.utils

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

fun generateReplyKeyboardWithOptions(options: List<String>): ReplyKeyboard {
    val rows = mutableListOf<KeyboardRow>()

    for (option in options) {
        val row = KeyboardRow()
        row.add(KeyboardButton(option))

        rows.add(row)
    }

    return ReplyKeyboardMarkup(rows)
}

fun generateInlineKeyboardWithOptions(options: List<String>): ReplyKeyboard {
    val rows = mutableListOf<InlineKeyboardRow>()

    for (option in options) {
        val row = InlineKeyboardRow()
        row.add(InlineKeyboardButton(option))

        rows.add(row)
    }

    return InlineKeyboardMarkup(rows)
}