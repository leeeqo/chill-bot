package com.oli.node.state

import com.oli.node.misc.Answer

sealed interface SessionMode {}

data class InterviewSession(
    var topicName: String? = null, //Topic? = null,
    var subtopicName: String? = null, //Subtopic? = null,
    //val questionIds: MutableList<Long> = mutableListOf(), //Question> = mutableListOf()
    val alreadyAnswered: MutableMap<Long, Answer> = mutableMapOf()
) : SessionMode