package com.oli.node.entity

import jakarta.persistence.*
import kotlinx.serialization.Serializable

@Entity
@Table(name = "subtopic")
@Serializable
data class Subtopic(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "name")
    val name: String,

    @ManyToOne
    @JoinColumn(name = "topic_id")
    val topic: Topic,

    @OneToMany(mappedBy = "subtopic", cascade = [CascadeType.ALL])
    val questions: List<Question> = listOf()
)