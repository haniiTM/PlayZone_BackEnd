package ru.playzone

import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import ru.playzone.features.login.configureLoginRouting
import ru.playzone.features.register.configureRegisterRouting
import ru.playzone.plugins.configureRouting
import ru.playzone.plugins.configureSerialization

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
    configureRegisterRouting()
    configureLoginRouting()

    configureSerialization()
}