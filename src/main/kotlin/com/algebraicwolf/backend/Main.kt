package com.algebraicwolf.backend

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.routing.*

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080) {
        routing {
            get("/") {
                println("Hello from Ktor!")
            }
        }
    }.start(wait = true)
}