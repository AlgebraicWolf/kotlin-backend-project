package com.algebraicwolf.backend.api

import com.algebraicwolf.backend.api.model.RegistrationRequest
import com.algebraicwolf.backend.api.model.UserRequest
import com.algebraicwolf.backend.storage.UserStorage
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Application.authApi() {
    routing {
        val storage by inject<UserStorage>()
        post("/register") {
            val user = call.receive<RegistrationRequest>()

            if (storage.register(user.username, user.password, user.name)) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.Forbidden, "User with the same username already exists")
            }
        }

        post("/login") {
            val user = call.receive<UserRequest>()

            if (storage.login(user.username, user.password)) {
                val token = JWT.create()
                    .withIssuer("http://0.0.0.0:8080/")
                    .withClaim("username", user.username)
                    .withExpiresAt(Date(System.currentTimeMillis() + 3600000))
                    .sign(Algorithm.HMAC256("secret"))
                call.respond(hashMapOf("token" to token))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Incorrect login or password")
            }
        }
    }
}