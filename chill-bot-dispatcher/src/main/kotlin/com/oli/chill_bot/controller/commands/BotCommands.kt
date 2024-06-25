package com.oli.chill_bot.controller.commands

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand
interface BotCommands {
    val listOfCommands: List<BotCommand>
}