package com.algebraicwolf.backend

import com.algebraicwolf.backend.api.authApi
import com.algebraicwolf.backend.api.publicationApi
import com.algebraicwolf.backend.extensions.initSchema
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.slf4j.event.Level

fun main(args: Array<String>) {
    configureDatabase()
    embeddedServer(Netty, port = 8080) {
        configureServer()
        publicationApi()
        authApi()
    }.start(wait = true)
}

private fun Application.configureServer() {
    install(Koin) {
        modules(publicationsModule, userStorageModule)
    }
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        })
    }
    install(CallLogging) {
        level = Level.DEBUG
    }
    install(Authentication) {
        jwt {
            realm = "myRealm"
            verifier(
                JWT
                    .require(Algorithm.HMAC256("secret"))
                    .withIssuer("http://0.0.0.0:8080/")
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is invalid or has expired")
            }
        }
    }
}

private fun configureDatabase() {
    val url = "jdbc:postgresql://localhost:9000/blog_db"
    val username = "user"
    val password = "password"
    val database = Database.connect(url = url, user = username, password = password)
    database.initSchema()
}