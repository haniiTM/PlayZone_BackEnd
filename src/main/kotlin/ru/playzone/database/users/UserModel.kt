package ru.playzone.database.users

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table("user") {
    private val login = Users.varchar("login", 25)
    private val password = Users.varchar("password", 25)
    private val username = Users.varchar("username", 25)
    private val email = Users.varchar("email", 25)

    fun createUser(userDTO: UserDTO) {
        transaction {
            Users.insert {
                it[login] = userDTO.login
                it[password] = userDTO.password
                it[username] = userDTO.username
                it[email] = userDTO.email ?: "test"
            }
        }
    }

    fun readUser(login: String): UserDTO? {
        return try {
            transaction {
                val user = Users.select {
                    Users.login.eq(login)
                }.single()

                UserDTO(
                    user[Users.login],
                    user[password],
                    user[username],
                    user[email],
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}