package ru.playzone.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class TestJSON(val text: String)

fun Application.configureRouting() {
    routing {
        get("/") {
//            call.respondText("Hello, World")
            call.respond(
                listOf(
                    TestJSON("hello"),
                    TestJSON("there"),
                )
            )
        }
    }
}