package com.oli.node.dao

import com.oli.node.entity.Subtopic
import com.oli.node.entity.Topic
import org.springframework.data.jpa.repository.JpaRepository

interface SubtopicDAO : JpaRepository<Subtopic, Long> {
    fun findAllByTopic(topic: Topic): List<Subtopic>

    fun findAllByName(name: String): List<Subtopic>
}