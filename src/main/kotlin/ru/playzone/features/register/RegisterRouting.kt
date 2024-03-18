package ru.playzone.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.playzone.cache.InMemoryCache
import ru.playzone.cache.TokenCache
import ru.playzone.utils.isValidEmail
import java.util.*

fun Application.configureRegisterRouting() {
    routing {
        post("/register") {
            val receive = call.receive<RegisterReceiveRemote>()

            if (!receive.email.isValidEmail()) {
                call.respond(HttpStatusCode.BadRequest, "Invalid e-mail!")
            }

            val user = InMemoryCache.userList.firstOrNull { it.login == receive.login }
            if (user != null) {
                call.respond(HttpStatusCode.Conflict, "User already exists!")
            } else {
                val token = UUID.randomUUID().toString()
                val tokenCache = TokenCache(receive.login, token)

                InMemoryCache.userList.add(receive)
                InMemoryCache.tokenList.add(tokenCache)

                call.respond(RegisterResponseRemote(token))
            }
        }
    }
}