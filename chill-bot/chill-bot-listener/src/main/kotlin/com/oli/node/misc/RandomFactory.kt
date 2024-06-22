package com.oli.node.misc

import kotlin.random.Random

class RandomFactory(chatId: String) {
    private val hashChatId: Int

    init {
        hashChatId = chatId.hashCode()
    }

    fun createRandom(seed: String): Random = Random(hashChatId + seed.hashCode())
}