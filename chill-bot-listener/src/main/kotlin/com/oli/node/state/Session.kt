package com.oli.node.state

import org.springframework.data.redis.core.RedisHash

//@Serializable
@RedisHash("Session")
data class Session (
    val id: String,
    val mode: Mode,
    var stage: Int,
    val subSession: SubSession,
    //val randomFactory: RandomFactory = RandomFactory(id)
    //val random: Random = Random(id.hashCode())
)