package com.oli.node.state

import com.oli.node.entity.Question
import com.oli.node.entity.Subtopic
import com.oli.node.entity.Topic

//@Serializable
sealed interface SessionMode {}

//@Serializable
data class InterviewSession(
    var topic: Topic? = null,
    var subtopic: Subtopic? = null,
    val questions: MutableList<Question> = mutableListOf()
) : SessionMode {
    fun getIdOfQuestions(): List<Long> = questions.stream().map { it.id }.toList()
}