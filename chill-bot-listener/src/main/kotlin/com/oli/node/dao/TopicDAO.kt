package com.oli.node.dao

import com.oli.node.entity.Topic
import org.springframework.data.jpa.repository.JpaRepository

interface TopicDAO : JpaRepository<Topic, Long> {

    fun findAllByName(name: String): List<Topic>
}