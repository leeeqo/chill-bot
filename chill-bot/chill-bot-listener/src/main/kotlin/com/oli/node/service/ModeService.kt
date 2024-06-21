package com.oli.node.service

import com.oli.node.state.Session
import org.telegram.telegrambots.meta.api.objects.Update

interface ModeService {
    fun getCurrentSession(update: Update): Session
    fun createNewSession(update: Update): Session
}