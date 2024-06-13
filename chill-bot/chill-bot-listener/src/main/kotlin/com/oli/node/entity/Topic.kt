package com.oli.node.entity

import jakarta.persistence.*

@Entity
@Table(name = "topic")
class Topic(
    @Id
    @GeneratedValue//(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "name")
    var name: String? = null,

    //@OneToMany(cascade = [CascadeType.ALL])
    //@JoinColumn(name = "topic_id")
    //val subtopics: List<Subtopic> = listOf(),
)