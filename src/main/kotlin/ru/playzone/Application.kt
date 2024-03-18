package ru.playzone

import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import ru.playzone.plugins.configureRouting

fun main() {
    embeddedServer(
        CIO,
        port = 8080,
        host = "0.0.0.0",
        module = Application::playzoneModule
    ).start(wait = true)
}

fun Application.playzoneModule() {
    configureRouting()
}