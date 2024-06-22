package com.oli.node.dao

import com.oli.node.entity.Question
import com.oli.node.entity.Subtopic
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionDAO : JpaRepository<Question, Long> {
    fun countBySubtopic(subtopic: Subtopic): Long
}