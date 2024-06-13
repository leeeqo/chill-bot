package com.oli.node.service

import org.telegram.telegrambots.meta.api.objects.Update

interface ConsumerService {
    fun consumeTopicListRequests(update: Update)

    //fun consumeDocMessageUpdates(update: Update)

    //fun consumePhotoMessageUpdates(update: Update)
}