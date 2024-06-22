package com.oli.node.state

//@Serializable
sealed interface SessionMode {}

//@Serializable
data class InterviewSession(
    var topic: String? = null, //Topic? = null,
    var subtopic: String? = null, //Subtopic? = null,
    val questions: MutableList<Long> = mutableListOf() //Question> = mutableListOf()
) : SessionMode {
    //fun getIdOfQuestions(): List<Long> = questions.stream().map { it.id }.toList()
}