package com.oli.node.service

import com.oli.node.service.ModeService
import org.telegram.telegrambots.meta.api.objects.Update

interface InterviewPrepareService: ModeService {

    fun processInterviewPrepareMessage(update: Update)
}