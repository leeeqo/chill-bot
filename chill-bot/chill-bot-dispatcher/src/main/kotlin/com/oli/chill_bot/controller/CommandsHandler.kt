package com.oli.chill_bot.controller

//import com.oli.chill_bot.controller.commands.StartCommand


/*@Component
class CommandsHandler(
    private val startCommand: StartCommand,
    private val helpCommand: HelpCommand
) {
    private val commands: Map<String, BotCommand>

    init {
        commands = java.util.Map.of<String, BotCommand>(
            "/start", startCommand,
            "/help", helpCommand
        )
    }

    fun handleCommands(update: Update): SendMessage {
        val messageText = update.message.text
        val command = messageText.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        val chatId = update.message.chatId
        val commandHandler: BotCommand? = commands[command]
        return if (commandHandler != null) {
            commandHandler.apply(update)
        } else {
            SendMessage(chatId.toString(), Consts.UNKNOWN_COMMAND)
        }
    }
}*/