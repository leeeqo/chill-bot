package com.oli.node.dao

import com.oli.node.entity.Topic
import org.springframework.data.jpa.repository.JpaRepository

interface TopicDAO : JpaRepository<Topic, Long> {

    //@Query("SELECT * FROM topic WHERE topic.name = :name LIMIT 1")
    //fun findByName(@Param("name") name: String): List<Topic>

    fun findAllByName(name: String): List<Topic>
}