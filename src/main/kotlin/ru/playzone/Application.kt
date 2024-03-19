package ru.playzone

import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database
import ru.playzone.features.login.configureLoginRouting
import ru.playzone.features.register.configureRegisterRouting
import ru.playzone.plugins.configureRouting
import ru.playzone.plugins.configureSerialization

fun main() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/postgres",
        "org.postgresql.Driver",
        "postgres",
        "postgres"
    )

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