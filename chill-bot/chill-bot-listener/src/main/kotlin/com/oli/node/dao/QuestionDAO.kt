package com.oli.node.dao

import com.oli.node.entity.Question
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionDAO : JpaRepository<Question, Long> {}