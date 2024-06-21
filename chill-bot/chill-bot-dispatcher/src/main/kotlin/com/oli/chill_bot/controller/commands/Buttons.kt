package com.oli.chill_bot.controller.commands

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow

class Buttons {
    companion object {
        private val START_BUTTON = InlineKeyboardButton("Start")
        private val HELP_BUTTON = InlineKeyboardButton("Help")
        private val END_BUTTON = InlineKeyboardButton("End")
    }

    fun inlineMarkup(): InlineKeyboardMarkup {
        START_BUTTON.callbackData = "/start"
        HELP_BUTTON.callbackData = "/help"
        END_BUTTON.callbackData = "/end"

        val rowInLine = listOf(START_BUTTON, END_BUTTON, HELP_BUTTON)
        val rowsInLine = InlineKeyboardRow(rowInLine)

        return InlineKeyboardMarkup(listOf(rowsInLine))
    }
}