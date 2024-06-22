package com.oli.node.entity

import jakarta.persistence.*

@Entity
@Table(name = "subtopic")
//@Serializable
data class Subtopic(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "name")
    val name: String = "",

    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn(name = "topic_id")
    val topic: Topic = Topic(),

    @OneToMany(mappedBy = "subtopic", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val questions: List<Question> = listOf()
)