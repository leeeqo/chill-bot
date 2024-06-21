package com.oli.node.state

import kotlinx.serialization.Serializable

@Serializable
data class Session (
    val chatId: String,
    val mode: Mode,
    val stage: Int,
    val sessionMode: SessionMode
)