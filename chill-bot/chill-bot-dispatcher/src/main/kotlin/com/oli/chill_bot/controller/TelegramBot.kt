package com.oli.chill_bot.controller

import com.oli.chill_bot.controller.commands.BotCommands
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

private val kLogger = KotlinLogging.logger {}

@Controller
class TelegramBot(
    @Value("\${bot.token}")
    private val token: String,
    private val updateController: UpdateController
): SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer, BotCommands {

    private val telegramClient = OkHttpTelegramClient(token)
    override val listOfCommands: List<BotCommand> = listOf(
        BotCommand("/start", "Start bot"),
        BotCommand("/end", "End session"),
        BotCommand("/help", "Bot info")
    )

    init {
        updateController.registerBot(this)

        try {
            // Commands
            telegramClient.execute(SetMyCommands(listOfCommands))

            /* Removing Keyboard
            telegramClient.execute(SendMessage.builder()
                .replyMarkup(ReplyKeyboardRemove(true))
                .build())*/
        } catch(e: TelegramApiException) {
            kLogger.error { e.message }
        }
    }

    override fun consume(update: Update?) {
        if (update == null) {
            kLogger.error { "Received update is null" }
            return
        }

        updateController.processUpdate(update)
    }

    fun sendAnswerMessage(sendMessage: SendMessage) {
        try {
            println("Message: ${sendMessage}")
            println("It has text? : ${sendMessage.text}")
            println("It has replyMarkup? : ${sendMessage.replyMarkup ?: "No, it doesn't"}")
            println("replyMarkup type? : ${sendMessage.replyMarkup?.javaClass ?: "No, it doesn't"}")

            telegramClient.execute(sendMessage)
        } catch (e: TelegramApiException) {
            kLogger.error { e.message }
        }
    }

    override fun getBotToken(): String = token

    override fun getUpdatesConsumer(): LongPollingUpdateConsumer = this

    /*private void botAnswerUtils(String receivedMessage, long chatId, String userName) {
        switch (receivedMessage){
            case "/start":
                startBot(chatId, userName);
                break;
            case "/help":
                sendHelpText(chatId, HELP_TEXT);
                break;
            default: break;
        }
    }

    private void startBot(long chatId, String userName) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Hi, " + userName + "! I'm a Telegram bot.'");
        message.setReplyMarkup(Buttons.inlineMarkup());

        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }

    private void sendHelpText(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);

        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }*/
}