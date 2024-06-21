package com.oli.node.service

import org.telegram.telegrambots.meta.api.objects.Update

interface MainService {
    fun processTextMessage(update: Update)//, session: Session)
}