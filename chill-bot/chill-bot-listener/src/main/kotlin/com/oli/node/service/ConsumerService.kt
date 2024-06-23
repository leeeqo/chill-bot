package com.oli.node.service

import org.telegram.telegrambots.meta.api.objects.Update

interface ConsumerService {

    fun consumeRequests(update: Update)
}