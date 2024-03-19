package ru.playzone.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.playzone.database.users.Users
import java.util.*

class LoginController(private val call: ApplicationCall) {
    suspend fun loginUser() {
        val receive = call.receive<LoginReceiveRemote>()

//        val user = InMemoryCache.userList.firstOrNull { it.login == receive.login }
        val userDTO = Users.readUser(receive.login)

        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, "I don't know you!")
        } else {
            if (userDTO.password != receive.password) {
                call.respond(HttpStatusCode.BadRequest, "Incorrect password!")
            } else {
                val token = UUID.randomUUID().toString()

//                val tokenDTO = TokenDTO(
//                    UUID.randomUUID().toString(),
//                    userDTO.login,
//                    token,
//                )

                call.respond(LoginResponseRemote(token))
            }
        }
    }
}