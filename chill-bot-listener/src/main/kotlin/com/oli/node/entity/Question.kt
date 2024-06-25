package com.oli.node.entity

import jakarta.persistence.*

@Entity
@Table(name = "question")
data class Question(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "text")
    val text: String = "",

    @Column(name = "level")
    val level: Int = -1,

    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn(name = "topic_id")
    val topic: Topic = Topic(),

    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn(name = "subtopic_id")
    val subtopic: Subtopic = Subtopic()
)