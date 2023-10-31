package com.algebraicwolf.backend.api

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.routing.get
import io.ktor.server.routing.put
import io.ktor.server.routing.patch
import io.ktor.server.routing.delete
import io.ktor.server.routing.routing

fun Application.publicationApi() {
    routing {
        put("/publication/create") {

        }

        get("/publication/{id}/get") {

        }

        patch("/publication/{id}/update") {

        }

        delete("/publication/{id}/delete") {

        }
    }
}