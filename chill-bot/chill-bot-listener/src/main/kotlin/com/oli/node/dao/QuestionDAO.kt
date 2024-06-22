package com.oli.node.dao

import com.oli.node.entity.Question
import com.oli.node.entity.Subtopic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface QuestionDAO : JpaRepository<Question, Long> {
    fun countBySubtopic(subtopic: Subtopic): Long

    @Query(nativeQuery = true, value =
            "SELECT * FROM question" +
            "WHERE question.id NOT IN :already_asked_ids" +
            "AND question.subtopic_id IN (" +
                "SELECT subtopic.id" +
                "FROM subtopic" +
                "WHERE subtopic.name = :subtopic_name" +
                "LIMIT 1" +
            ")")
    fun findAllBySubtopicExceptAlreadyAsked(
        @Param("subtopic_name") subtopicName: String,
        @Param("already_asked_ids") alreadyAskedIds: List<Long>
    ) : List<Question>

    @Query(nativeQuery = true, value =
            "SELECT * FROM question " +
            "WHERE question.subtopic_id IN (" +
                "SELECT subtopic.id " +
                "FROM subtopic " +
                "WHERE subtopic.name = :subtopic_name " +
                "LIMIT 1" +
            ") " +
            "ORDER BY RANDOM() " +
            "LIMIT 1")
    fun findRandBySubtopic(@Param("subtopic_name") subtopicName: String): List<Question>

    @Query(nativeQuery = true, value =
            "SELECT * FROM question " +
            "WHERE question.id NOT IN :already_asked_ids " +
            "AND question.subtopic_id IN (" +
                "SELECT subtopic.id " +
                "FROM subtopic " +
                "WHERE subtopic.name = :subtopic_name " +
                "LIMIT 1" +
            ") " +
            "ORDER BY RANDOM() " +
            "LIMIT 1")
    fun findRandBySubtopicExceptAlreadyAsked(
        @Param("subtopic_name") subtopicName: String,
        @Param("already_asked_ids") alreadyAskedIds: List<Long>
    ) : List<Question>
}