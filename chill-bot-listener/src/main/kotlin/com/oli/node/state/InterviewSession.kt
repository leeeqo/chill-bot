package com.oli.node.state

import com.oli.node.misc.Answer

data class InterviewSession(
    var topicName: String? = null, //Topic? = null,
    var subtopicName: String? = null, //Subtopic? = null,
    // TO_DO - change to collection like Map, but with access to last added key-value
    val alreadyAsked: MutableList<Long> = mutableListOf(),//LinkedHashMap<Long, Answer?> = linkedMapOf()
    val answers: MutableList<Answer> = mutableListOf()
) : SubSession()