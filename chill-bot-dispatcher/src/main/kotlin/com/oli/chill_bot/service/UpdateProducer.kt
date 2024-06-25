package com.oli.chill_bot.service

import org.telegram.telegrambots.meta.api.objects.Update

interface UpdateProducer {
    fun produce(queue: String, update: Update)
}