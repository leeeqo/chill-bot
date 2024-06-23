package com.oli.node.state

import org.springframework.data.redis.core.RedisHash

//@Serializable
@RedisHash("Session")
data class Session (
    val id: String,
    //val chatId: String,
    val mode: Mode,
    var stage: Int,
    val sessionMode: SessionMode,
    //val randomFactory: RandomFactory = RandomFactory(id)
    //val random: Random = Random(id.hashCode())
)