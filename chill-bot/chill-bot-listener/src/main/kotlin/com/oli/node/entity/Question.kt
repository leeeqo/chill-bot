package com.oli.node.entity

import jakarta.persistence.*
import kotlinx.serialization.Serializable

@Entity
@Table(name = "question")
@Serializable
data class Question(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "text")
    val text: String,

    @Column(name = "level")
    val level: Int,

    @ManyToOne
    @JoinColumn(name = "topic_id")
    val topic: Topic,

    @ManyToOne
    @JoinColumn(name = "subtopic_id")
    val subtopic: Subtopic
)