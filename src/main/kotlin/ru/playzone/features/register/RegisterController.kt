package ru.playzone.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import ru.playzone.database.tokens.TokenDTO
import ru.playzone.database.tokens.Tokens
import ru.playzone.database.users.UserDTO
import ru.playzone.database.users.Users
import ru.playzone.utils.isValidEmail
import java.util.*

class RegisterController(private val call: ApplicationCall) {
    suspend fun registerUser() {
        val receive = call.receive<RegisterReceiveRemote>()

        if (!receive.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest, "Invalid e-mail!")
        }

//        val user = InMemoryCache.userList.firstOrNull { it.login == receive.login }
        val userDTO = Users.readUser(receive.login)

        if (userDTO != null) {
            call.respond(HttpStatusCode.Conflict, "User already exists!")
        } else {
            val token = UUID.randomUUID().toString()

//            val tokenCache = TokenCache(receive.login, token)
//
//            InMemoryCache.userList.add(receive)
//            InMemoryCache.tokenList.add(tokenCache)

            val userDTO = UserDTO(
                receive.login,
                receive.password,
                "",
                receive.email,
            )

            val tokenDTO = TokenDTO(
                UUID.randomUUID().toString(),
                receive.login,
                token
            )

            try {
                Users.createUser(userDTO)
            } catch (e: ExposedSQLException) {
                call.respond(HttpStatusCode.Conflict, "User already exists!")
            }

            Tokens.createToken(tokenDTO)

            call.respond(RegisterResponseRemote(token))
        }
    }
}