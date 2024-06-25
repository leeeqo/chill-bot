package com.oli.node.entity

import jakarta.persistence.*

@Entity
@Table(name = "topic")
data class Topic(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "name")
    val name: String = "",

    @OneToMany(mappedBy = "topic", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val subtopics: List<Subtopic> = listOf(),

    @OneToMany(mappedBy = "topic", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val questions: List<Question> = listOf()
)