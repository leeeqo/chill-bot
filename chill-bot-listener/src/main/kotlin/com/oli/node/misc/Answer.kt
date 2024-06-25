package com.oli.node.misc

enum class Answer(val text: String) {
    GOOD("Good"),
    MUST_BE_REPEATED("Must be repeated"),
    DO_NOT_KNOW("Don't know");

    companion object {
        fun from(text: String): Answer = entries.find { it.text == text } ?:
            throw IllegalArgumentException("There is no Answer enum for $text")
    }
}