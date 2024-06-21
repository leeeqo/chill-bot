package com.oli.node.dao

import com.oli.node.entity.Subtopic
import org.springframework.data.jpa.repository.JpaRepository

interface SubtopicDAO : JpaRepository<Subtopic, Long> {}