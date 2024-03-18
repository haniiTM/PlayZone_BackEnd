package ru.playzone.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.playzone.cache.InMemoryCache
import java.util.*

fun Application.configureLoginRouting() {
    routing {
        post("/login") {
            val receive = call.receive<LoginReceiveRemote>()
            val user = InMemoryCache.userList.firstOrNull { it.login == receive.login }

            if (user == null) {
                call.respond(HttpStatusCode.BadRequest, "I don't know you!")
            } else {
                if (user.password != receive.password) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect password!")
                } else {
                    val token = UUID.randomUUID().toString()

                    call.respond(LoginResponseRemote(token))
                }
            }
        }
    }
}