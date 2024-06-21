package com.oli.node.state

import com.oli.node.entity.Question
import com.oli.node.entity.Subtopic
import com.oli.node.entity.Topic
import kotlinx.serialization.Serializable

@Serializable
sealed interface SessionMode {}

@Serializable
data class InterviewSession(
    val topic: Topic? = null,
    val subtopic: Subtopic? = null,
    val questions: List<Question?> = listOf()
) : SessionMode