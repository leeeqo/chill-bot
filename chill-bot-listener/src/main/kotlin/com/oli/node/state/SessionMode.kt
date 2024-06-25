package com.oli.node.state

sealed interface SessionMode {}

data class InterviewSession(
    var topicName: String? = null, //Topic? = null,
    var subtopicName: String? = null, //Subtopic? = null,
    val questionIds: MutableList<Long> = mutableListOf() //Question> = mutableListOf()
) : SessionMode