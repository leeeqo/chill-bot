package com.oli.node.utils

import com.oli.node.state.Mode

fun defineModeByText(text: String): Mode {
    for (entry in Mode.entries) {
        if (text == entry.text)
            return entry
    }

    return Mode.UNDEFINED
}