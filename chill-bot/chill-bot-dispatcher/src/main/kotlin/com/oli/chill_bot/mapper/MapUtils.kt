package com.oli.chill_bot.mapper

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup

fun ReplyKeyboardMarkup.options(): List<String> = keyboard.stream()
    .flatMap { it.stream() }
    .map { it.text }
    .toList()