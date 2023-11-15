package com.algebraicwolf.backend.extensions

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun ApplicationCall.getPathParameter(name: String): String? {
    return this.parameters[name]
}

fun ApplicationCall.getCurrentUser(): String {
    val principal = this.principal<JWTPrincipal>()
    return principal!!.payload.getClaim("username").asString()
}