package com.oli.chill_bot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChillBotDispatcherApplication

fun main(args: Array<String>) {
	runApplication<ChillBotDispatcherApplication>(*args)
}
