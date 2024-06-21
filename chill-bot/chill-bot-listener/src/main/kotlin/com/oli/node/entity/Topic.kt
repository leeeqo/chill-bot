package com.oli.node.entity

import jakarta.persistence.*
import kotlinx.serialization.Serializable

@Entity
@Table(name = "topic")
@Serializable
data class Topic(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "name")
    val name: String,

    @OneToMany(mappedBy = "topic", cascade = [CascadeType.ALL])
    val subtopics: List<Subtopic> = listOf(),

    @OneToMany(mappedBy = "topic", cascade = [CascadeType.ALL])
    val questions: List<Question> = listOf()
)